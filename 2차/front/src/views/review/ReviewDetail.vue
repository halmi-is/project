<template>
  <div>
    <div class="d-flex align-items-center justify-content-between">
      <div>
        <h2 style="margin-bottom: 20px; font-weight: bold">숙박업소후기</h2>
        <h5 style="margin-bottom: 20px">
          숙박업소를 다녀온 고객분들의 솔직한 후기
        </h5>
      </div>
      <div class="image-container" style="margin-right: 20px">
        <img
          src="/images/icon-7680929_1920.png"
          alt="따봉"
          style="width: 200px; height: 200px; object-fit: contain; margin-right: 40px;"
        />
      </div>
    </div>
    <br />

    <!-- 제목 -->
    <h3 v-if="review.title" style="font-weight: bold; padding: 3px">
      {{ review.title }}
    </h3>



    <div
      class="d-flex justify-content-between align-items-center"
      style="margin-top: 10px"
    >
      <div class="d-flex align-items-center">
        <p style="margin-right: 20px; margin-top: 10px">
          평점:
          <template v-for="star in 5" :key="star">
            <i
              class="bi"
              :class="star <= review.rating ? 'bi-star-fill' : 'bi-star'"
              style="color: #ffd700"
            ></i>
          </template>
        </p>
      </div>
      <div style="margin-left: auto; text-align: right">
        <span>작성일 : {{ formatDate(review.createdAt) }}</span>
        <span style="margin-left: 20px">작성자 : {{ review.authorEmail }}</span>
      </div>
    </div>
    <hr />
        <!-- 선택된 상품명 -->
        <p v-if="review.targetName" style="font-size: 1.2rem; font-weight: bold; color: #555; margin-top: 5px;">
      상품명: {{ review.targetName }}
    </p>
    <hr>

    <br />
    <div class="mb-3 col-12">
      <img :src="review.imageUrl" width="400px" alt="이미지" />
    </div>
    <p>{{ review.content }}</p>

    <br />
    <div class="d-flex justify-content-end">
      <button
        v-if="review.authorEmail === userEmail"
        type="button"
        class="btn btn-secondary"
        @click="goToUpdateReview(review.reviewId)"
        style="font-size: 1.5rem; font-weight: bold; padding: 8px 40px; margin-right: 20px;"
      >
        수정
      </button>

      <button
        v-if="review.authorEmail === userEmail || userRole === 'ROLE_ADMIN'"
        type="button"
        class="btn btn-danger"
        @click="remove"
        style="font-size: 1.5rem; font-weight: bold; padding: 8px 40px; margin-right: 20px;"
      >
        삭제
      </button>

      <button
        type="button"
        class="btn btn-warning"
        @click="goToAddReview"
        style="font-size: 1.5rem; font-weight: bold; padding: 8px 40px"
      >
        목록
      </button>
    </div>

    <!-- 하단 카드 (주의사항) -->
    <div class="card w-100 mt-5 mb-5">
      <div class="card-body">
        <h5 class="card-title"><i class="bi bi-exclamation-circle"></i> 꼭 읽어주세요</h5>
        <hr />
        <p class="card-text mt-4" style="font-size: 14px; padding: 20px;">
          - 글 작성 시 정보 유출에 의한 피해방지를 위해 개인정보 기재는 삼가주시기 바랍니다.
          예) 주민등록번호, 전화번호, 여권번호, 신용카드번호, 계좌번호, 주소 등
          <br />
          - 해당 게시판과 글의 성격이 맞지 않을 경우, 관리자에 의해 게시글이 이동될 수 있습니다.
          <br />
          - 상업적인 광고 및 욕설, 도배성, 음해성 글의 경우 서비스 관리자에 의해 임의 수정 또는 삭제될 수 있음을 알려드립니다.
          <br />
          - 저작권 등 다른 사람의 권리를 침해하거나 명예를 훼손하는 게시글은 이용약관 및 관련법률에 의해 제재를 받을 수 있습니다.
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import ReviewService from "@/services/review/ReviewService";
import MainService from "@/services/main/MainService";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      review: {
        reviewId: this.$route.params.reviewId,
        rating: 0,
        title: "",
        content: "",
        authorEmail: "",
        targetId: null,
        targetName: "", // 상품명 추가
        imageUrl: "",
        image: undefined,
      },
      userEmail: "",
      userRole: "",
    };
  },
  methods: {
    formatDate(date) {
      const options = { year: "numeric", month: "2-digit", day: "2-digit" };
      return new Date(date).toLocaleDateString("ko-KR", options);
    },
    async getDetail(reviewId) {
      try {
        let response = await ReviewService.get(reviewId); // 리뷰 상세 정보 가져오기
        this.review = response.data;

        // 상품명을 추가로 가져오기
        if (this.review.targetId) {
          const targetResponse = await MainService.getName(this.review.targetId);
          this.review.targetName = targetResponse.data; // 상품명 저장
        }
      } catch (error) {
        console.error("리뷰 상세 정보를 가져오는 중 오류가 발생했습니다:", error);
      }
    },
    select() {
      this.review.image = this.$refs.file.files[0];
    },
    goToAddReview() {
      this.$router.push("/review");
    },
    goToUpdateReview(reviewId) {
      this.$router.push(`/reviewupdate/${reviewId}`);
    },
    async update() {
      try {
        let response = await ReviewService.update(
          this.review.reviewId,
          this.review
        );
        console.log(response.data);
        Swal.fire({
          title: "수정 완료",
          icon: "success",
          confirmButtonText: "확인",
        });
      } catch (error) {
        console.log(error);
      }
    },
    async remove() {
      try {
        let response = await ReviewService.remove(this.review.reviewId);
        console.log(response.data);
        Swal.fire({
          title: "삭제 성공",
          icon: "success",
          confirmButtonText: "확인",
        });
        this.$router.push("/review");
      } catch (error) {
        console.log(error);
      }
    },
  },
  mounted() {
    this.getDetail(this.$route.params.reviewId); // 리뷰 정보 가져오기

    const user = localStorage.getItem("user");
    if (user) {
      const parsedUser = JSON.parse(user);
      this.userRole = parsedUser.codeName;
      this.userEmail = parsedUser.email;
    } else {
      console.error("No user data found in localStorage.");
    }
  },
};
</script>

<style>
/* 추가 스타일 필요 시 작성 */
</style>
