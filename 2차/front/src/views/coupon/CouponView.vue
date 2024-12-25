<template>
  <div class="container mt-5">
    <!-- 대표 호텔 쿠폰 섹션 -->
    <div class="row justify-content-center my-4">
      <div class="col-md-6">
        <div class="card border-primary shadow-custom">
          <div class="card-body">
            <!-- 쿠폰 헤더 -->
            <div class="d-flex justify-content-between align-items-center mb-3">
              <h5 class="card-title text-primary fw-bold">
                신규회원 한정 20% 할인
              </h5>
            </div>

            <!-- 쿠폰 정보 -->
            <div class="d-flex align-items-center mb-3">
              <div
                class="discount-circle bg-gradient-primary text-light border-primary fw-bold"
              >
                {{ coupon.value }}%
              </div>
              <div class="ms-3">
                <p class="mb-1 text-muted">최소 구매금액 제한 없음</p>
              </div>
            </div>

            <!-- 추가 정보 -->
            <ul class="list-unstyled text-muted mb-4">
              <li>신규회원을 위한 특별 쿠폰</li>
              <li>적용 대상: 24년 11월 25일 이후의 가입자</li>
              <li>쿠폰 기한 60일</li>
            </ul>

            <!-- 쿠폰 받기 버튼 -->
            <button class="btn btn-gradient-primary w-100" @click="getCoupon">
              신규회원 한정 쿠폰 받기
            </button>
          </div>
        </div>
      </div>
    </div>

    
  </div>
</template>

<script>
import Swal from "sweetalert2";
import CouponService from "@/services/coupon/CounponService";

export default {
  data() {
    return {
      coupon: {
        value: 20.0,
        name: "",
        memberEmail: "",
      },
    };
  },

  methods: {
    async getCoupon() {
      try {
        let response = await CouponService.insert(this.coupon);
        console.log(response.data);
        // 성공하면 강제이동

        Swal.fire({
          title: "쿠폰 발급 성공!",
          text: "결제페이지에서 쿠폰을 사용하세요.",
          icon: "success",
          confirmButtonText: "확인",
        });

        this.$router.push("/");
      } catch (error) {
        console.error("쿠폰 발급 중 오류 발생:", error);
       
        Swal.fire({
          title: "쿠폰 발급 실패!",
          text: "이미 쿠폰을 받으셨습니다.",
          icon: "error",
          confirmButtonText: "확인",
        });


      }
    },
  },
};
</script>

<style scoped>
/* Google Fonts */
@import url("https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap");

/* 기본 폰트 설정 */
body {
  font-family: "Roboto", sans-serif;
}

/* 할인율 동그라미 스타일 */
.discount-circle {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  font-size: 28px;
  font-weight: bold;
  border-radius: 50%;
  border: 3px solid;
  text-shadow: 1px 1px 2px rgba(50, 50, 50, 50);
}

/* 그라데이션 효과를 추가한 버튼 */
.btn-gradient-primary {
  background: linear-gradient(to right, #007bff, #00bfff);
  color: white;
  border: none;
}

.btn-gradient-info {
  background: linear-gradient(to right, #17a2b8, #20c997);
  color: white;
  border: none;
}

/* 카드 스타일 */
.card {
  border-radius: 15px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.card-body {
  padding: 20px;
}

/* 카드 hover 시 애니메이션 효과 */
.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

/* 섹션 간격 및 버튼 */
.btn {
  padding: 12px;
  font-size: 16px;
  font-weight: 500;
}

.card-title {
  font-size: 1.25rem;
}

.card-body ul {
  font-size: 14px;
}

.card-body li {
  margin-bottom: 5px;
}

/* 노란색 써클 */
.discount-circle {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  font-size: 28px;
  font-weight: bold;
  border-radius: 50%;
  border: 3px solid yellow !important; /* 강제로 노란색 테두리 적용 */
  color: #ffd700; /* 텍스트 색상 노란색 */
  background: linear-gradient(
    to bottom right,
    #fffacd,
    #ffeb3b
  ); /* 배경 노란색 */
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5); /* 텍스트 그림자 */
}

/* 카드 박스 외곽선 노란색 */
.card.border-primary,
.card.border-info {
  border-color: yellow !important; /* 노란색 테두리 */
}

/* 버튼 노란색 */
.btn-gradient-primary,
.btn-gradient-info {
  background: linear-gradient(
    to right,
    #ffdd00,
    #ffcc00
  ); /* 노란색 그라데이션 */
  color: white;
  border: none;
}

.btn-gradient-primary:hover,
.btn-gradient-info:hover {
  background: linear-gradient(
    to right,
    #ffd700,
    #ffc107
  ); /* hover 시 진한 노란색 */
}

/* 카드 hover 효과 */
.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(255, 223, 0, 0.5); /* 노란색 그림자 */
}
</style>
