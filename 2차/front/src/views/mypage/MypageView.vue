<template>
<div class="container mt-5">
  <!-- 사용자 정보 -->
  <div class="card mb-4">
    <div class="card-header bg-warning text-white">
      <h4>프로필</h4>
    </div>
    <div>
      <div class="card-body">
        <ul class="list-group">
          <li class="list-group-item">
            <strong>Email:</strong> {{ userEmail }}
          </li>
        </ul>
      </div>
    </div>
  </div>

  <!-- 쿠폰 목록 -->
  <h5 class="text-warning">나의 쿠폰</h5>
  <table class="table table-striped">
    <thead class="bg-warning text-white">
      <tr>
        <th>#</th>
        <th>쿠폰 ID</th>
        <th>할인율</th>
        <th>쿠폰명</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(coupon, index) in coupons" :key="index">
        <td>{{ index + 1 }}</td>
        <td>{{ coupon.id }}</td>
        <td>{{ coupon.value }}%</td>
        <td>{{ coupon.name }}</td>
      </tr>
    </tbody>
  </table>


<!-- 리뷰 목록 -->
<h5 class="text-warning">나의 리뷰</h5>
<table class="table table-striped">
  <thead class="bg-warning text-white">
    <tr>
      <th>#</th>
      <th>제목</th>
      <th>작성일</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="(review, index) in reviews" :key="review.reviewId">
      <td>{{ index + 1 }}</td>
      <td>
        <!-- 리뷰 제목을 클릭하면 해당 리뷰의 상세 페이지로 이동 -->
        <router-link
          :to="'/review/' + review.reviewId"
          class="review-link"
        >
          {{ review.title }}
        </router-link>
      </td>
      <td>{{ formatDate(review.createdAt) }}</td>
    </tr>
  </tbody>
</table>



</div>
</template>

<script>
import CouponService from "@/services/coupon/CounponService";
import MypageService from "@/services/mypage/MypageService";
import ReviewService from "@/services/review/ReviewService";

export default {
  data() {
    return {
      pageIndex: 1, //현재페이지번호
      totalCount: 0, // 전체개수
      recordCountPerPage: 1, //화면에 보일개수
      searchKeyword: "",
      mypages: [], // 빈배열(json)
      userEmail: "", // 이메일 초기화
      coupons: [],
      reviews: [], // 리뷰 데이터 저장
    };
  },

  methods: {
    async getMypage() {
      try {
        let response = await MypageService.getAll(
          this.searchKeyword,
          this.pageIndex - 1,
          this.recordCountPerPage
        );
        // TODO: 백엔드 전송되는 것 : results(배열), totalCount(총개수)
        const { results, totalCount } = response.data;
        console.log(response.data); // 디버깅
        this.mypages = results;
        this.totalCount = totalCount;
      } catch (error) {
        console.log(error);
      }
    },

    formatDate(date) {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(date).toLocaleDateString('ko-KR', options);
    },


    async fetchCoupons() {
    try {
      console.log("Fetching coupons...");
      const response = await CouponService.getCouponsByEmail();
      console.log("Coupons fetched successfully:", response.data);
      this.coupons = response.data; // 가져온 쿠폰 데이터 저장
    } catch (error) {
      console.error("쿠폰 조회 실패:", error); // 에러 로그 출력
      if (error.response) {
        // 서버에서 응답이 왔으나 실패한 경우
        console.error("Response error:", error.response.status, error.response.data);
      } else if (error.request) {
        // 요청이 전송되었으나 응답이 없을 경우
        console.error("No response received:", error.request);
      } else {
        // 요청 설정 중에 발생한 에러
        console.error("Request setup error:", error.message);
      }
    }
  },
  async fetchReviews() {
  const email = this.userEmail;
  if (email) {
    try {
      console.log("Fetching reviews for email: ", email); // 이메일 확인
      const response = await ReviewService.getReviewsByEmail(email);
      console.log("Reviews fetched successfully:", response.data); // 응답 확인
      this.reviews = response.data; // 가져온 리뷰 데이터를 reviews에 저장
    } catch (error) {
      console.error("리뷰 조회 실패:", error);
      // 에러 상세 출력
      if (error.response) {
        console.error("Response error:", error.response.status, error.response.data);
      } else if (error.request) {
        console.error("No response received:", error.request);
      } else {
        console.error("Request setup error:", error.message);
      }
    }
  }
}






  },

  mounted() {
    this.getMypage();
    this.fetchCoupons();
    const user = JSON.parse(localStorage.getItem("user"));
  if (user && user.email) {
    this.userEmail = user.email; // 이메일 저장
    this.fetchReviews(); // 사용자의 리뷰 데이터를 가져옴// 로그인한 사용자 리뷰 가져오기
  } else {
    this.$router.push("/login"); // 로그인되지 않은 사용자는 로그인 페이지로 이동
  }
  },
};
</script>

<style scoped>
.card-header {
  background-color: #f8c102; /* 노란색 */
}

.nav-tabs .nav-link.active {
  background-color: #f8c102;
  color: white;
}

.nav-tabs .nav-link {
  border-color: #f8c102;
}

.table th {
  background-color: #f8c102;
  color: white;
}

.table-striped tbody tr:nth-child(odd) {
  background-color: #fef7e2;
}

/* 리뷰 제목 링크 스타일 */
.review-link {
  text-decoration: none;  /* 기본 밑줄 제거 */
  color: #f8c102;         /* 노란색으로 링크 색상 지정 */
  font-weight: bold;      /* 링크 제목 강조 */
  transition: color 0.3s; /* 마우스 오버 시 색상이 변하는 효과 */
}

.review-link:hover {
  color: #d8a102; /* 마우스를 올리면 색상 변경 */
}

/* 테이블에서 배경색 및 스타일 설정 */
.table th, .table td {
  text-align: center; /* 내용 중앙 정렬 */
}

.table-striped tbody tr:nth-child(odd) {
  background-color: #fef7e2; /* 홀수 번째 행에 배경색 추가 */
}
</style>
