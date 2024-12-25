<template>
  <div class="cart-container">
    <h1 class="cart-title">장바구니(현재: {{ cartCount }})</h1>

    <!-- 전체 선택 및 선택 삭제 버튼 -->
    <div class="cart-actions">
      <label>
        <input
          type="checkbox"
          @change="toggleSelectAll"
          :checked="isAllSelected"
          :disabled="cartCount === 0"
        />
        전체 선택
      </label>
      <button @click="deleteSelectedItems" class="delete-selected-button">
        선택 삭제
      </button>
    </div>

    <!-- 장바구니 아이템 목록 -->
    <div v-if="cart.length > 0" class="cart-items">
      <div class="cart-item" v-for="(data, index) in cart" :key="index">
        <!-- 이미지 섹션 -->
        <div class="cart-item-image-container">
          <!-- 이미지 상단 왼쪽 체크박스 -->
          <input
            type="checkbox"
            v-model="selectedItems"
            :value="data.cartId"
            class="cart-item-checkbox"
            @change="updateCartData"
          />
          <img
            :src="data.tourFileUrl"
            alt="Tour Image"
            class="cart-item-image"
          />
        </div>

        <!-- 정보 섹션 -->
        <div class="cart-item-details">
          <button
            class="cart-item-remove"
            @click="deleteFromCartId(data.cartId)"
          >
            &times;
            <!-- X 아이콘 -->
          </button>
          <h2 class="cart-item-title" @click="makeReservation(data)">
            {{ data.tourName }}
          </h2>
          <p class="cart-item-room">{{ data.roomName }}</p>
          <p class="cart-item-capacity">인원(기준) : {{ data.capacity }}명</p>
          <p class="cart-item-checkin">
            체크인 : {{ data.checkInDate }} {{ data.checkInTime }}
          </p>
          <p class="cart-item-checkout">
            체크아웃 : {{ data.checkOutDate }} {{ data.checkOutTime }}
          </p>
          <p class="cart-item-duration">
            숙박 일수 : {{ data.stayDuration }}박
          </p>
          <p class="cart-item-price" style="font-weight: 900; color: #e74c3c">
            결제 금액 : {{ data.totalPrice }}원
          </p>
        </div>
      </div>
    </div>
    <div v-else class="cart-empty">
      <p>장바구니가 비어 있습니다.</p>
    </div>
  </div>

  <!-- Sticky 버튼 박스 -->
  <div class="sticky-box">
    <div class="sticky-box-info">
      <p class="left">총 {{ selectedItems.length }}건</p>
      <!--  체크된 아이템의 개수 -->
      <p class="right">총 결제 금액 {{ formatPrice(totalPrice) }}원</p>
      <!--  체크된 아이템의 총 가격 -->
    </div>
    <button
      class="sticky-box-button"
      style="
        margin-top: 20px;
        height: 50px;
        background-color: #e74c3c;
        font-size: 20px;
        color: white;
        border: none;
        border-radius: 10px;
      "
      @click="goToPaymentPage"
    >
      구매하기
    </button>
  </div>
</template>

<script>
import CartService from "@/services/cart/CartService";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      userEmail: "",
      user: "",
      cart: [],
      cartCount: 0, // 장바구니 개수
      selectedItems: [], // 선택된 cartId 리스트
      isAllSelected: false, // 전체 선택 상태
      totalPrice: "", // 총 가격
    };
  },
  methods: {
    async getItemsFromEmail(userEmail) {
      try {
        const response = await CartService.getItemsFromEmail(userEmail);
        this.cart = response.data;
        this.calculateTotalPrice(); // 장바구니 아이템 로드 후 총 가격 계산
        this.countCartItems(); // 장바구니 개수 업데이트
      } catch (error) {
        console.error("Error fetching cart:", error);
      }
    },

    // 개별 삭제
    async deleteFromCartId(cartId) {
      try {
        // 서버에서 삭제 요청
        await CartService.deleteFromCartId(cartId);

        // 로컬 상태에서 아이템 제거
        this.cart = this.cart.filter((item) => item.cartId !== cartId);

        // 선택 항목에서 삭제된 항목 제거
        this.selectedItems = this.selectedItems.filter((id) => id !== cartId);

        // 총 가격 및 장바구니 개수 업데이트
        this.calculateTotalPrice();
        this.countCartItems();
        this.updateSelectAllStatus();

        // 삭제 완료 메시지 표시
        await Swal.fire({
          icon: "success",
          title: "삭제되었습니다.",
          text: "선택한 상품이 장바구니에서 삭제되었습니다.",
          showConfirmButton: false, // 확인 버튼 제거
          timer: 1500, // 1.5초 후 자동으로 닫힘
        });
      } catch (error) {
        console.error("Error deleting item:", error);
        // 오류 발생 시 사용자 알림
        await Swal.fire({
          icon: "error",
          title: "삭제 실패",
          text: "삭제 중 문제가 발생했습니다. 다시 시도해주세요.",
          showConfirmButton: false, // 확인 버튼 제거
          timer: 1500, // 1.5초 후 자동으로 닫힘
        });
      }
    },

    // 선택 삭제
    async deleteSelectedItems() {
      if (this.selectedItems.length === 0) {
        await Swal.fire({
          icon: "warning",
          title: "선택된 상품이 없습니다.",
          text: "삭제할 상품을 선택해주세요.",
          showConfirmButton: false, // 확인 버튼 제거
          timer: 1500, // 1.5초 후 자동으로 닫힘
        });
        return;
      }

      try {
        // 선택된 항목들을 삭제 요청
        await Promise.all(
          this.selectedItems.map((cartId) =>
            CartService.deleteFromCartId(cartId)
          )
        );

        // 선택된 항목들을 장바구니에서 제거
        this.cart = this.cart.filter(
          (item) => !this.selectedItems.includes(item.cartId)
        );

        this.isAllSelected = false; // 전체 선택 초기화

        // 삭제 완료 메시지 표시
        await Swal.fire({
          icon: "success",
          title: "삭제되었습니다.",
          text: "선택된 상품이 장바구니에서 삭제되었습니다.",
          showConfirmButton: false, // 확인 버튼 제거
          timer: 1500, // 1.5초 후 자동으로 닫힘
        });

        // 가격 업데이트 및 장바구니 개수 갱신
        this.calculateTotalPrice();
        this.countCartItems();
        this.selectedItems = []; // 선택 항목 초기화
      } catch (error) {
        console.error("Error deleting selected items:", error);

        // 오류 발생 시 사용자 알림
        await Swal.fire({
          icon: "error",
          title: "삭제 실패",
          text: "선택된 상품 삭제 중 문제가 발생했습니다. 다시 시도해주세요.",
          showConfirmButton: false, // 확인 버튼 제거
          timer: 1500, // 1.5초 후 자동으로 닫힘
        });
      }
    },

    // 전체 선택/해제
    toggleSelectAll() {
      // 전체 선택 버튼을 클릭시, isAllSelected가 true일 때는 전체 선택을 해제하고, false일 때는 전체 항목을 선택하여 selectedItems에 추가
      if (this.isAllSelected) {
        this.selectedItems = []; // 전체 선택 해제
      } else {
        // 모든 항목의 cartId를 selectedItems 배열에 추가하여 전체 선택
        this.selectedItems = this.cart.map((item) => item.cartId);
      }
      // 전체 선택 상태를 반전시킴
      this.isAllSelected = !this.isAllSelected;
      // 전체 선택 후, 선택된 항목들의 총 가격을 다시 계산
      this.calculateTotalPrice();
    },

    // 장바구니 항목 데이터 업데이트
    updateCartData() {
      // 선택된 항목들의 총 가격을 계산
      this.calculateTotalPrice();
      // 장바구니 개수를 다시 계산
      this.countCartItems();
      // 개별 체크박스 상태 변경 후 전체 선택 상태를 업데이트
      this.updateSelectAllStatus();
    },

    // 전체 선택 상태 업데이트
    updateSelectAllStatus() {
      // 전체 체크박스를 선택하기 위해, 모든 항목이 선택되었는지 확인
      // cart 배열에 포함된 모든 항목이 selectedItems 배열에 포함되어 있을 때 this.isAllSelected를 true로 설정 => 전체 체크박스 체크됨
      this.isAllSelected =
        this.cart.length > 0 &&
        this.cart.every((item) => this.selectedItems.includes(item.cartId));
    },

    calculateTotalPrice() {
      // 선택된 항목들의 총 가격 계산
      this.totalPrice = this.cart
        .filter((item) => this.selectedItems.includes(item.cartId))
        .reduce((acc, item) => {
          // item.totalPrice가 문자열일 경우 쉼표를 제거하고 숫자로 변환
          let price = Number(item.totalPrice.replace(/,/g, ""));
          // 가격이 NaN일 경우 0으로 처리
          return acc + (isNaN(price) ? 0 : price);
        }, 0); // 문자열을 숫자로 변환하고 합산
    },

    formatPrice(price) {
      // 숫자에 쉼표를 추가하는 함수
      // price가 유효하지 않으면 "0"을 반환
      if (price === undefined || price === null || isNaN(price)) {
        return "0";
      }
      return price.toLocaleString();
    },

    async countCartItems() {
      try {
        const response = await CartService.countCartItems(this.userEmail);
        this.cartCount = response.data;
        localStorage.setItem("cartCount", this.cartCount.toString());
      } catch (error) {
        console.error("Error counting cart items:", error);
      }
    },

    // 개별 결제
    makeReservation(data) {
      const reservation = {
        totalPrice: data.totalPrice,
        userEmail: data.userEmail,
        stayDuration: data.stayDuration,
        tourFileUrl: data.tourFileUrl,
        tourName: data.tourName,
        roomName: data.roomName,
        capacity: data.capacity,
        checkInDate: data.checkInDate,
        checkInTime: data.checkInTime,
        checkOutDate: data.checkOutDate,
        checkOutTime: data.checkOutTime,
      };

      localStorage.setItem(
        `reservation_${data.cartId}`,
        JSON.stringify(reservation)
      );

      this.$router.push(`/cart/payment/${data.cartId}`);
    },

    // 체크된 숙소들 단체 결제
    async goToPaymentPage() {
      try {
        // 선택된 상품이 없는 경우 알림
        if (this.selectedItems.length === 0) {
          await Swal.fire({
            icon: "warning",
            title: "선택된 상품 없음",
            text: "선택된 상품이 없습니다.",
          });
          return;
        }

        // 선택된 상품들의 데이터를 서버에서 조회
        const selectedItemsData = await Promise.all(
          this.cart
            .filter((item) => this.selectedItems.includes(item.cartId)) // 선택된 cartId만 필터링
            .map(async (item) => {
              const response = await CartService.getItemsFromCartId(
                item.cartId
              );
              return response.data; // 서버에서 받은 데이터 반환
            })
        );

        // 로컬스토리지에 선택된 상품 데이터를 저장
        localStorage.setItem(
          "selectedItems",
          JSON.stringify(selectedItemsData)
        );

        // 성공 메시지 및 결제 페이지로 이동
        const result = await Swal.fire({
          icon: "success",
          title: "결제 준비 완료",
          text: "선택된 상품을 결제 페이지로 이동합니다.",
          showCancelButton: true,
          confirmButtonText: "결제 페이지로 이동",
          cancelButtonText: "취소",
        });

        if (result.isConfirmed) {
          this.$router.push("/TotalPayment");
        } else {
          console.log("결제 페이지 이동이 취소되었습니다.");
        }
      } catch (error) {
        console.error("Error fetching cart items:", error);
        await Swal.fire({
          icon: "error",
          title: "오류 발생",
          text: "결제 페이지로 이동 중 오류가 발생했습니다. 다시 시도해주세요.",
        });
      }
    },
  },
  mounted() {
    const user = localStorage.getItem("user");
    if (user) {
      const parsedUser = JSON.parse(user);
      this.user = parsedUser;
      this.userEmail = parsedUser.email;
    } else {
      console.error("No user data found in localStorage.");
    }

    this.getItemsFromEmail(this.userEmail);
    this.countCartItems(); // 장바구니 개수 계산
  },
};
</script>

<style scoped>
.sticky-box {
  position: sticky;
  bottom: 0;
  width: 100%; /* 화면 너비에 맞게 확장 */
  max-width: 800px; /* 최대 너비 설정 */
  height: 150px;
  background-color: #f1f1f1;
  padding: 10px;
  text-align: center;
  z-index: 10;
  margin: 0 auto; /* 중앙 정렬 */
}

.sticky-box-button {
  width: 100%; /* 부모 너비에 맞게 100% 확장 */
  max-width: 800px; /* 최대 너비 설정 */
  margin: 0 auto; /* 중앙 정렬 */
}

.sticky-box-info {
  display: flex;
  justify-content: space-between;
  width: 100%; /* 부모 너비에 맞게 100% 확장 */
  max-width: 800px; /* 최대 너비 설정 */
  margin: 0 auto; /* 중앙 정렬 */
  text-align: center; /* 텍스트 중앙 정렬 */
}

.sticky-box-info p {
  margin: 0;
  font-size: 20px;
  font-weight: 800;
}

.cart-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding-bottom: 25px; /* sticky-box가 화면 하단에 고정될 수 있도록 공간을 확보 */
}

.cart-title {
  text-align: center;
  font-size: 1.8rem;
  margin-bottom: 20px;
  color: #333;
}

/* 전체 선택/삭제 버튼 */
.cart-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.delete-selected-button {
  color: #3498db; /* 파란색 글씨 */
  background-color: transparent; /* 배경색 제거 */
  border: none;
  font-size: 1rem;
  cursor: pointer;
  transition: color 0.3s, transform 0.2s ease;
}

.delete-selected-button:hover {
  color: #2980b9; /* 호버 시 글씨 색 변경 */
  transform: scale(1.05); /* 호버 시 약간 확대 */
}

.cart-item {
  display: flex;
  position: relative;
  background-color: white;
  padding: 15px;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.cart-item-image-container {
  width: 60%; /* 이미지의 가로 크기를 60%로 설정 */
  height: auto; /* 높이는 자동으로 설정 */
  overflow: hidden;
  border-radius: 8px;
  position: relative;
}

.cart-item-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.cart-item-details {
  width: 40%; /* 정보란의 가로 크기를 40%로 설정 */
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.cart-item-image-container input[type="checkbox"] {
  position: absolute;
  top: 10px;
  left: 10px;
  width: 20px;
  height: 20px;
  z-index: 2;
}

.cart-item-remove {
  position: absolute; /* 절대 위치 */
  top: 5px; /* 상단에서 5px */
  right: 15px; /* 우측에서 5px */
  background: none; /* 배경 제거 */
  border: none; /* 테두리 제거 */
  font-size: 1.8rem; /* 글씨 크기 감소 */
  cursor: pointer; /* 커서 변경 */
  transition: color 0.2s ease;
}

.cart-item-title {
  font-size: 1.4rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.cart-item-room,
.cart-item-duration,
.cart-item-checkin,
.cart-item-checkout,
.cart-item-price {
  font-size: 1.1rem;
  margin: 5px 0;
}

.cart-item-buttons {
  display: flex;
  gap: 10px;
  margin-top: 15px;
  justify-content: flex-start;
}

.cart-item-buy {
  padding: 10px 20px;
  font-size: 1rem;
  border-radius: 25px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s ease;
}

.cart-empty {
  text-align: center;
  color: #999;
  font-size: 1.2rem;
}

.cart-item-title {
  cursor: pointer; /* 마우스를 올리면 손가락 모양으로 변경 */
  transition: all 0.3s ease; /* 부드러운 애니메이션 효과 */
}

.cart-item-title:hover {
  color: #007bff; /* 마우스를 올렸을 때 색상 변경 */
  text-decoration: underline; /* 텍스트에 밑줄 추가 */
}
</style>
