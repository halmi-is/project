<template>
  <div>
    <!-- data.commentText -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="commentText"
        placeholder="commentText"
        v-model="comments.commentText"
      />
      <label for="dname">commentText</label>
    </div>

    <!-- Dropdown for Location -->
    <div class="form-floating mb-3">
      <select class="form-select" id="commentLoc" v-model="comments.commentLoc">
        <option value="" disabled selected>Choose a location</option>
        <option value="홋카이도 소베츠 수조원">홋카이도 소베츠 수조원</option>
        <option value="니스 성 니콜라스 성당">니스 성 니콜라스 성당</option>
        <option value="해운대구 해마루">해운대구 해마루</option>
      </select>
      <label for="commentLoc">Location</label>
    </div>


    <!-- Star Rating -->
    <div class="mb-3">
      <label for="rating" class="form-label">별점</label>
      <div class="star-rating" id="rating">
        <span
          v-for="n in 5"
          :key="n"
          class="star"
          :class="{ 'text-warning': n <= comments.rating, 'text-muted': n > comments.rating }"
          style="font-size: 1.5rem; cursor: pointer;"
          @click="setRating(n)"
        >
          ★
        </span>
      </div>
    </div>






    <!-- 수정버튼 -->
    <button type="button" class="btn btn-warning" @click="update">수정</button>
    <!-- 삭제버튼 -->
    <button
      type="button"
      class="btn btn-danger"
      @click="remove"
      style="margin-left: 8px"
    >
      삭제
    </button>
  </div>
</template>
<script>
import CommentsService from "@/services/recommend/CommentsService";
export default {
  data() {
    return {
      comments: {
        comId: "",
        commentText: "",
        commentLoc: "",
        rating: 1, // 기본값
      },
    };
  },
  methods: {
    // 함수작성
    // TODO: 상세조회
    async getDetail(comId) {
      try {
        let response = await CommentsService.get(comId);
        console.log(response.data);
        this.comments = response.data;
      } catch (error) {
        console.log(error);
      }
    },
    //TODO: 수정
    async update() {
      try {
        let response = await CommentsService.update(
          this.comments.comId,
          this.comments
        );
        console.log(response.data);
        this.$router.push("/recommend/");
      } catch (error) {
        console.log(error);
      }
    },

    setRating(rating) {
      this.comments.rating = rating;
    },

    /* //TODO: 삭제 */

    async remove() {
      try {
        let response = await CommentsService.remove(this.comments.comId);
        console.log(response.data);
        this.$router.push("/recommend");
      } catch (error) {
        console.log(error);
      }


      
    },
  },

  mounted() {
    this.getDetail(this.$route.params.comId);
  },
};
</script>
<style></style>
