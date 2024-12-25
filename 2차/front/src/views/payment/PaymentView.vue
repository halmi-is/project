<template>
  <div class="payment-page">
    <h2 style="margin-bottom: 30px; font-weight: 900">예약</h2>

    <div v-if="reservation" class="reservation-details">
      <div class="image-container">
        <img
          :src="reservation.tourFileUrl"
          alt="숙소 이미지"
          class="tour-image"
        />
      </div>

      <div class="details-container">
        <h3 style="font-weight: 900">{{ reservation.tourName }}</h3>
        <p class="room-name">
          {{ reservation.roomName }}
        </p>
        <p class="capacity">인원(기준) : {{ reservation.capacity }}명</p>

        <div class="dates">
          <p>
            체크인 : {{ reservation.checkInDate }} {{ reservation.checkInTime }}
          </p>
          <p>
            체크아웃 : {{ reservation.checkOutDate }}
            {{ reservation.checkOutTime }}
          </p>
        </div>

        <div class="pricing">
          <p>숙박 일수 : {{ reservation.stayDuration }}박</p>
          <p class="total-price">
            총 결제 금액 : {{ reservation.totalPrice }}원
          </p>
        </div>
      </div>
    </div>

    <!-- 쿠폰등록하기 버튼 -->
    <div class="mt-3">
      <button class="btn btn-warning" @click="toggleCouponForm">
        쿠폰등록하기
      </button>
    </div>

    <!-- 쿠폰 선택 폼 -->
    <div
      v-if="showCouponForm && coupons !== null"
      class="mt-3 p-3 bg-light border rounded"
    >
      <h5>쿠폰 선택</h5>
      <div
        class="list-group-item d-flex justify-content-between align-items-center"
      >
        <span>{{ coupons.name }} ({{ coupons.value }}%)</span>
        <button class="btn btn-primary btn-sm" @click="selectCoupon(coupons)">
          선택
        </button>
      </div>
    </div>

    <!-- 쿠폰이 없을 경우 안내 메시지 -->
    <p v-if="coupons === null && showCouponForm">
      현재 사용 가능한 쿠폰이 없습니다.
    </p>

    <!-- 선택된 쿠폰 표시 -->
    <div v-if="selectedCoupon" class="mt-3 alert alert-success">
      선택된 쿠폰: {{ selectedCoupon.name }} ({{ selectedCoupon.value }}%)
    </div>

    <!-- 결제 버튼 섹션 -->
    <div class="payment-section mt-4">
      <button class="payment-button primary-button" @click="processPayment">
        {{ reservation.totalPrice }}원 결제하기
      </button>
      <button class="payment-button secondary-button" @click="returnPage">
        뒤로가기
      </button>
    </div>
  </div>
</template>

<script>
import CouponService from "@/services/coupon/CounponService";
import PortOne from "@portone/browser-sdk/v2";
import TossService from "@/services/toss/TossService";
import Swal from "sweetalert2";
export default {
  data() {
    return {
      reservation: {}, // 예약 정보 저장 객체

      selectedPaymentMethod: null, // 선택된 결제 수단

      showCouponForm: false, // 쿠폰 폼 표시 여부

      coupons: null,

      pageIndex: 1, //현재페이지번호
      totalCount: 0, // 전체개수
      recordCountPerPage: 1, //화면에 보일개수
      searchKeyword: "",
      selectedCoupon: null, // 선택된 쿠폰
      value: 20.0,
      name: "",
      id: "",
      toss: {
        // Store ID 설정
        storeId: "store-37adc342-491c-4a84-ae08-08fe128442bb",
        // 채널 키 설정
        channelKey: "channel-key-0f8548f7-3030-42ee-b5e4-fce98be8af2f",
        paymentId: `payment-${crypto.randomUUID()}`,
        order: {
          name: "라마다 호텔",
          amount: 90870,
        },
        orderName: "",
        totalAmount: "",
        currency: "CURRENCY_KRW",
        payMethod: "CARD",
      },

      userEmail: "",
    };
  },
  mounted() {
    // URL에서 roomId 파라미터 가져오기
    const roomId = this.$route.params.roomId;

    // 해당 roomId에 대한 예약 정보를 로컬 스토리지에서 가져오기
    const reservationData = localStorage.getItem(`reservation_${roomId}`);

    if (reservationData) {
      this.reservation = JSON.parse(reservationData);
    } else {
      alert("예약 정보가 없습니다.");
      this.$router.push("/"); // 예약 정보가 없으면 홈으로 리다이렉트
    }

    // 사용자가 쿠폰을 보유한 경우 쿠폰 데이터를 로드asd
    const user = localStorage.getItem("user");
    if (user) {
      const parsedUser = JSON.parse(user);
      this.user = parsedUser;
      this.userEmail = parsedUser.email;
    } else {
      console.error("No user data found in localStorage.");
    }

    this.getCoupon();
  },

  methods: {
    returnPage() {
      // 이전 페이지로 이동
      this.$router.go(-1);
    },

    // 쿠폰 폼 토글
    toggleCouponForm() {
      if (this.coupons === null) {
        Swal.fire({
          title: "알림",
          text: "현재 사용 가능한 쿠폰이 없습니다.",
          icon: "info", // 아이콘 유형: 'success', 'error', 'warning', 'info', 'question'
          confirmButtonText: "확인",
        });
      } else {
        this.showCouponForm = !this.showCouponForm;
      }
    },

    // 쿠폰 선택
    selectCoupon(coupon) {
      this.selectedCoupon = coupon;

      if (this.reservation.originalPrice === undefined) {
        this.reservation.originalPrice = parseInt(
          this.reservation.totalPrice.replace(/,/g, ""),
          10
        );
      }

      const discountRate = coupon.value / 100;
      const discountedPrice =
        Math.floor(this.reservation.originalPrice * (1 - discountRate));
      this.reservation.totalPrice = discountedPrice.toLocaleString();
      this.showCouponForm = false; // 폼 닫기
    },
    // 이메일로 쿠폰 조회
    async getCoupon() {
      try {
        let response = await CouponService.couponByEmail(this.userEmail);
        // 쿠폰이 하나라면 객체로 저장
        if (response.data && response.data.name && response.data.value) {
          this.coupons = response.data;
        } else {
          this.coupons = null; // 쿠폰이 없으면 null 처리
        }
        console.log("Coupons:", this.coupons); // 디버깅
      } catch (error) {
        console.log(error);
        if (error.response) {
          console.log("Error response:", error.response);
        }
        console.log("User Email:", this.userEmail); // 이메일 출력
      }
    },

    async processPayment() {
      try {
        // 쉼표를 제거하고 정수형으로 변환
        const sanitizedPrice = parseInt(
          this.reservation.totalPrice.replace(/,/g, ""),
          10
        );
        this.toss.order.amount = sanitizedPrice; // 정수형으로 업데이트
        this.toss.totalAmount = sanitizedPrice; // 정수형으로 업데이트
        this.toss.orderName = this.reservation.tourName;
        const response = await PortOne.requestPayment(this.toss);
        if (response.code !== undefined) {
          //오류 발생
          return alert(response.message);
        }

        TossService.notified(this.toss)
          .then((response) => console.log(response))
          .catch((error) => console.error(error.response.data));
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>

<style scoped>
.payment-page {
  padding: 20px;
  font-family: Arial, sans-serif;
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

h2 {
  margin-bottom: 20px;
}

.reservation-details {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 50px;
  margin: 0 auto;
  max-width: 900px;
  text-align: left;
}

.image-container {
  flex: 1;
  max-width: 300px;
  text-align: center;
}

.tour-image {
  width: 300px;
  height: 100%;
  border-radius: 8px;
  object-fit: cover;
}

.details-container {
  flex: 2;
  max-width: 300px;
}

h3 {
  font-size: 1.8em;
  margin-bottom: 10px;
}

.capacity {
  margin-bottom: 10px;
}

.pricing {
  font-size: 1.4em;
}

.total-price {
  color: #e74c3c;
  font-weight: bold;
}

.payment-method {
  margin-top: 20px;
}

.payment-options {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 10px;
}

.payment-option {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}

.payment-option input {
  display: none;
}

.payment-option img {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 50%;
  border: 2px solid transparent;
  transition: all 0.3s ease;
}

.payment-option input:checked + img {
  border-color: #ff4b2b;
  box-shadow: 0 0 8px rgba(255, 75, 43, 0.5);
}

.payment-option img:hover {
  transform: scale(1.1);
}

.payment-section {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-top: 20px;
}

.payment-button {
  padding: 12px 20px;
  font-size: 16px;
  font-weight: bold;
  border-radius: 25px;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

.payment-button:hover {
  transform: scale(1.05);
}

.payment-button:active {
  transform: scale(0.95);
}

/* 구매하기 버튼 스타일 */
.primary-button {
  background: linear-gradient(90deg, #ff4b2b, #ff416c);
  color: white;
}

.primary-button:hover {
  background: linear-gradient(90deg, #ff416c, #ff4b2b);
  box-shadow: 0 6px 8px rgba(255, 75, 43, 0.4);
}

.primary-button:active {
  box-shadow: 0 3px 5px rgba(255, 75, 43, 0.3);
}

/* 뒤로가기 버튼 스타일 */
.secondary-button {
  background: linear-gradient(90deg, #434343, #000000);
  color: white;
}

.secondary-button:hover {
  background: linear-gradient(90deg, #000000, #434343);
}
</style>
