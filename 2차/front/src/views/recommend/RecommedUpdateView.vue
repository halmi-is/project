<template>
  <div>
    <!-- tdName -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="tdName"
        placeholder="tdName"
        v-model="recommend.tdName"
      />
      <label for="tdName">tdName</label>
    </div>

    <!-- loc dd-->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="loc"
        placeholder="loc"
        v-model="recommend.loc"
      />
      <label for="loc">loc</label>
    </div>

    <!-- description -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="description"
        placeholder="description"
        v-model="recommend.description"
      />
      <label for="description">description</label>
    </div>

    <!-- TODO: 현재이미지 보기 -->

    <div class="mb-3">
      <img
        :src="recommend.imageUrl"
        class="card-img-top"
        alt="이미지"
        style="width: 50%"
      />
    </div>

    <!-- 파일선택상자 -->

    <div class="input-group">
      <input type="file" class="form-control" ref="file" @change="select" />
      <button class="btn btn-warning" type="button" @click="update">
        수정
      </button>
    </div>
    <div class="mt-3">
      <button class="btn btn-danger" type="button" @click="remove">삭제</button>
    </div>
  </div>
</template>
<script>
import RecommendService from "@/services/recommend/RecommendService";

export default {
  data() {
    return {
      recommend: {
        tdId: "",
        tdName: "",
        loc: "",
        description: "",
        imageUrl: "", //다운로드 url

        image: undefined, // 선택이미지
      },
    };
  },

  methods: {
    select() {
      //   this.fileDb.image = this.$refs.file.files[0];
      const file = this.$refs.file.files[0];
      if (file) {
        this.recommend.image = file;
        this.recommend.imageUrl = URL.createObjectURL(file); // 이미지 미리보기
      }
    },

    async getDetail(tdId) {
      try {
        let response = await RecommendService.get(tdId);
        console.log(response.data);
        this.recommend = response.data;
      } catch (error) {
        console.log(error);
      }
    },

    //TODO: 수정
    async update() {
      try {
        let response = await RecommendService.update(
          this.recommend.tdId,
          this.recommend
        );
        console.log(response.data);

        this.$router.push("/recommend").then(() => {
          window.location.reload(); // 자동 새로고침
        });

        alert("데이터 수정됨");
      } catch (error) {
        this.recommend.image = undefined;
        console.log(error);
      }
    },

    // 삭제
    async remove() {
      try {
        let response = await RecommendService.remove(this.recommend.tdId);
        console.log(response.data);
        alert("삭제됨");
        this.$router.push("/recommend");
      } catch (error) {
        console.log(error);
      }
    },
  },

  mounted() {
    this.getDetail(this.$route.params.tdId);
  },
};
</script>
<style></style>
