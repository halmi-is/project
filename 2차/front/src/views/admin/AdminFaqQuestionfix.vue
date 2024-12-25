<template>
  <div class="bigbox">
    <hr />
    <div class="announce_body_box">
      <div class="notice_container">
        <div class="notice_search">
          <br />
        </div>
        <div class="form-floating talk_content">
          <textarea type="text" class="form-control" id="validationDefault02" placeholder="내용" required
            style="height: 60px" v-model="faq.question"></textarea>
          <label for="validationDefault02">제목</label>
        </div>
        <br />
        <div class="form-floating talk_content">
          <textarea type="text" class="form-control" id="validationDefault02" placeholder="내용" required
            style="height: 100px" v-model="faq.answer"></textarea>
          <label for="validationDefault02">내용</label>
        </div>
        <br />
        <div class="button_box">
          <button type="button" class="btn btn-warning me-2" @click="update">
            수정
          </button>
          <!-- 삭제버튼 -->
          <button type="button" class="btn btn-danger" @click="remove">
            삭제
          </button>
        </div>
        <br />
      </div>
    </div>
  </div>
</template>

<script>
import FaqService from "@/services/faq/FaqService";

export default {
  data() {
    return {
      faq: {
        fno: "",
        question: "",
        answer: "",
        hashtag: "",
      },
    };
  },
  methods: {
    async getDetail(fno) {
      try {
        let response = await FaqService.get(fno);
        console.log(response.data); // 디버깅
        this.faq = response.data;
      } catch (error) {
        console.log(error);
      }
    },
    async update() {
      try {
        let response = await FaqService.update(this.faq.fno, this.faq);
        console.log(response.data); // 디버깅
        alert("수정 완료");
        this.$router.push("/mainadmin2");
      } catch (error) {
        console.log(error);
      }
    },
    async remove() {
      try {
        let response = await FaqService.remove(this.faq.fno);
        console.log(response.data); // 디버깅
        alert("삭제 완료");
        this.$router.push("/mainadmin2");
      } catch (error) {
        console.log(error);
      }
    },
  },
  mounted() {
    this.getDetail(this.$route.params.fno);
  },
};
</script>

<style>
.button_box {
  justify-content: end;
  display: flex;
}

.bigbox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.announce_body_box {
  width: 70%;
  border: 2.5px solid black;
  border-radius: 10px;
  padding: 15px;
}

.notice_search .search_text {
  width: 80%;
}

.faq-list {
  list-style: none;
  padding: 0;
}

.faq-item {
  margin-bottom: 15px;
}

.faq-item h3 {
  margin: 0;
}

.talk_big_title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
}
</style>
