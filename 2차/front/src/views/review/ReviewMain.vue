<template>
  <div>
    <!-- 상단 텍스트와 이미지 컨테이너 -->
    <div class="d-flex align-items-center justify-content-between">
      <div>
        <h2 style="margin-bottom: 20px; font-weight: bold;">숙박업소후기</h2>
        <h5 style="margin-bottom: 50px; ">숙박업소를 다녀온 고객분들의 솔직한 후기</h5>
      </div>
      <!-- <div class="image-container" style="margin-right: 20px">
        <img src="/images/icon-7680929_1920.png" alt="따봉"
          style="width: 200px; height: 200px; object-fit: contain; margin-right: 40px;" />
      </div> -->
    </div>
    <h5 style="margin-top: 50px; margin-bottom: 50px; font-weight: bold;">최근 베스트 여행후기</h5>
    <div
  id="carouselExampleAutoplaying"
  class="carousel slide"
  data-bs-ride="carousel"
  data-bs-interval="1500"
  style="margin-bottom: 20px"
>
  <div class="carousel-inner">
    <div
      class="carousel-item"
      v-for="(review, index) in topReviews"
      :key="review.reviewId"
      :class="{ active: index === 0 }"
    >
      <img
        :src="review.imageUrl || require('@/assets/images/침대.jpg')"
        class="d-block w-100 img-fluid rounded shadow"
        alt="Review Image"
      />
      <div class="carousel-caption d-none d-md-block">
        <h5 style="font-weight: bold; font-size: 1.1rem; cursor: pointer;" @click="goToReviewDetail(review.reviewId)">
          {{ review.title }}
        </h5>
        <p>
          {{ review.authorEmail }} / {{ formatDate(review.createdAt) }}
        </p>
        <span>
          <template v-for="star in 5" :key="star">
            <i class="bi" :class="star <= review.rating ? 'bi-star-fill' : 'bi-star'" style="color: #FFD700;"></i>
          </template>
        </span>
      </div>
    </div>
  </div>
  <button
    class="carousel-control-prev"
    type="button"
    data-bs-target="#carouselExampleAutoplaying"
    data-bs-slide="prev"
  >
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button
    class="carousel-control-next"
    type="button"
    data-bs-target="#carouselExampleAutoplaying"
    data-bs-slide="next"
  >
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>
    

    <div class="container mt-5">
      <div class="row">
        <div class="col-md-3" v-for="review in topReviews" :key="review.reviewId">
          <div class="card" style="width: 19rem; border: none;">
            <img :src="review.imageUrl || '@/assets/images/침대.jpg'" class="card-img-top" alt="..." width="200px" height="180px">
            <div class="card-body">
              <p class="card-text">
                <span>
                  <template v-for="star in 5" :key="star">
                    <i class="bi" :class="star <= review.rating ? 'bi-star-fill' : 'bi-star'"
                      style="color: #FFD700;"></i>
                  </template>
                </span><br>
                <span class="review-title" style="font-weight: bold; font-size: 1.1rem; cursor: pointer;"
                  @click="goToReviewDetail(review.reviewId)">
                  {{ review.title }}
                </span><br>
                {{ review.authorEmail }} / {{ formatDate(review.createdAt) }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="container mt-5">
      <table class="table table-hover table-with-top-border">
        <thead>
          <tr>
            <th scope="col" style="width: 10%; padding-bottom: 20px; padding-top: 20px; text-align: center;">번호</th>
            <th scope="col" style="width: 15%; padding-bottom: 20px; padding-top: 20px; text-align: center;">평점</th>
            <th scope="col" style="width: 50%; padding-bottom: 20px; padding-top: 20px; text-align: center;">제목</th>
            <th scope="col" style="width: 15%; padding-bottom: 20px; padding-top: 20px; text-align: center;">작성자</th>
            <th scope="col" style="width: 10%; padding-bottom: 20px; padding-top: 20px; text-align: center;">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(review, index) in reviews" :key="index">
            <th style="text-align: center;">{{ totalCount - (pageIndex - 1) * recodeCountPerPage - index }}</th>
            <td style="text-align: center;">
              <template v-for="star in 5" :key="star">
                <i class="bi" :class="star <= review.rating ? 'bi-star-fill' : 'bi-star'" style="color: #FFD700;"></i>
              </template>
            </td>
            <td style="cursor: pointer;" @click="goToReviewDetail(review.reviewId)">
              <span class="review-title">
                {{ review.title }}
                <span v-if="isNew(review.createdAt)" class="new-icon">N</span>
              </span>
            </td>
            <td style="text-align: center;">{{ review.authorEmail }}</td>
            <td style="text-align: center;">{{ formatDate(review.createdAt) }}</td>
          </tr>
        </tbody>
      </table>

      <!-- 페이지네이션 추가 -->
      <div class="d-flex justify-content-center mt-3">
        <b-pagination
          v-model="pageIndex"
          :total-rows="totalCount"
          :per-page="recodeCountPerPage"
          @click="getReview"
          class="pagination"
        ></b-pagination>
      </div>

      <!-- 글등록 버튼 추가 -->
      <div class="d-flex justify-content-end mt-3">
        <button type="button" class="btn btn-warning" @click="goToAddReview"
          style="font-size: 1.5rem; font-weight: bold; padding: 8px 40px;">
          글등록
        </button>
      </div>
    </div>

    <!-- 하단 코드 (예: 주의사항 카드) -->
    <div class="card w-100 mt-5 mb-5">
      <div class="card-body">
        <h5 class="card-title"> <i class="bi bi-exclamation-circle"></i> 꼭 읽어주세요</h5>
        <hr>
        <p class="card-text mt-4" style="font-size: 14px; padding: 20px;">- 글 작성 시 정보 유출에 의한 피해방지를 위해 개인정보 기재는 삼가주시기 바랍니다.
          예) 주민등록번호, 전화번호, 여권번호, 신용카드번호, 계좌번호, 주소 등
          <br>
          - 해당 게시판과 글의 성격이 맞지 않을 경우, 관리자에 의해 게시글이 이동될 수 있습니다.
          <br>
          - 상업적인 광고 및 욕설, 도배성, 음해성 글의 경우 서비스 관리자에 의해 임의 수정 또는 삭제될 수 있음을 알려드립니다.
          <br>
          - 저작권 등 다른 사람의 권리를 침해하거나 명예를 훼손하는 게시글은 이용약관 및 관련법률에 의해 제재를 받을 수 있습니다.
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import ReviewService from "@/services/review/ReviewService";

export default {
  data() {
    return {
      pageIndex: 1,
      totalCount: 0,
      recodeCountPerPage: 10,
      searchKeyword: "",
      reviews: [],
      
    };
  },
  computed: {
    topReviews() {
      return this.reviews
        .slice()
        .sort((a, b) => b.rating - a.rating)
        .slice(0, 4);
    },
  },
  methods: {
    async getReview() {
      try {
        let response = await ReviewService.getAll(
          this.searchKeyword,
          this.pageIndex - 1,
          this.recodeCountPerPage
        );
        const { results, totalCount } = response.data;
        this.reviews = results.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)); // 작성일 기준으로 정렬
        this.totalCount = totalCount;
      } catch (error) {
        console.error("리뷰 목록을 가져오는 중 오류가 발생했습니다:", error);
      }
    },
    goToAddReview() {
      const user = JSON.parse(localStorage.getItem("user"));
      console.log(user);
      if(user == null)
    {
      this.$router.push("/login");
    }else
      this.$router.push('/add-review');
    },
    formatDate(date) {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(date).toLocaleDateString('ko-KR', options);
    },
    goToReviewDetail(reviewId) {
      this.$router.push(`/review/${reviewId}`);
    },
    // 최근 작성된 글인지 확인하는 메서드
    isNew(createdAt) {
      const today = new Date();
      const createdDate = new Date(createdAt);
      const diffDays = Math.floor((today - createdDate) / (1000 * 60 * 60 * 24));
      return diffDays <= 1; // 작성일이 1일 이내라면 true 반환
    },
  },
  mounted() {
    this.getReview();
  },
};
</script>

<style>
.new-icon {
  background-color: red; /* 배경색 */
  color: white; /* 흰색 글자 */
  font-size: 0.6rem; /* 글자 크기 */
  font-weight: bold; /* 글자 굵기 */
  padding: 2px 4px; /* 안쪽 여백 */
  margin-left: 6px; /* 제목과의 간격 */
  border-radius: 20px; /* 둥근 모서리 */
}

.review-title {
  text-decoration: none; /* 기본 텍스트 장식 제거 */
}

.review-title:hover {
  text-decoration: underline; /* 마우스 오버 시 밑줄 추가 */
}

.pagination {
  display: flex;
  justify-content: center; /* 중앙 정렬 */
  margin: 20px 0; /* 위 아래 여백 */
}

.pagination .page-item {
  margin: 0 5px; /* 페이지 아이템 간격 */
}

.pagination .page-item .page-link {
  border: none; /* 테두리 제거 */
  background-color: transparent; /* 배경 색상 제거 */
  color: black; /* 링크 색상 */
  padding: 10px 15px; /* 여백 */
}

.pagination .page-item.active .page-link {
  background-color: #ffc107; /* 활성화된 페이지 링크 색상 */
  border-color: #ffc107; /* 활성화된 링크 테두리 색상 */
}

.pagination .page-link:hover {
  background-color: #e9ecef; /* 마우스 오버 색상 */
  color: tan; /* 마우스 오버 링크 색상 */
}

.table {
  border-top: 4px solid bisque; /* 테이블 상단 테두리 색상 */
}

/* 캐러셀 디자인 */
.carousel-inner img {
  height: 400px; /* 캐러셀 이미지 크기 조정 */
  object-fit: cover; /* 이미지 비율 유지 */
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
  filter: invert(1); /* 화이트 컬러로 아이콘 */
}

</style>
