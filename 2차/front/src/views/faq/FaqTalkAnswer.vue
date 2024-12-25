<template>
  <div class="bigbox">
    <hr />
    <div class="announce_body_box">
      <div class="notice_container">
        <div class="notice_search">
          <div class="notice_tab_area nav">
            <div class="sub_tab_list" role="tablist"></div>
          </div>
          <br />
        </div>
        <div class="notice_content_big_box">
          <p class="talk_big_title">문의 등록 목록 테스트</p>
          <br />
          <div class="form-floating talk_content">
            <textarea
              type="text"
              class="form-control"
              id="validationDefault02"
              placeholder="내용"
              required
              style="height: 250px"
              v-model="talk.reply"
            ></textarea>
            <label for="validationDefault02">내용</label>
          </div>
          <div class="talkanswer_button_box">
            <router-link :to="'/faq/talklist'">
              <button
                type="button"
                class="btn btn-warning me-2"
                @click="update"
              >
                저장
              </button> </router-link
            ><router-link :to="'/faq/talklist'">
              <button type="button" class="btn btn-danger me-2" @click="remove">
                삭제
              </button>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TalkService from "@/services/faq/TalkService";

export default {
  data() {
    return {
      talk: {
        tno: "",
        reply: "",
      },
    };
  },
  methods: {
    async getDetail(tno) {
      try {
        let response = await TalkService.get(tno);
        this.talk = response.data;
        console.log("답변 로드 성공:", response.data);
      } catch (error) {
        console.error("답변 로드 중 에러:", error);
      }
    },
    async update() {
      try {
        let response = await TalkService.updated(this.talk.tno, this.talk);
        console.log("답변 저장 성공:", response.data);
      } catch (error) {
        console.log(error);
      }
    },
    async remove() {
      try {
        const response = await TalkService.remove(this.talk.tno);
        console.log("삭제 성공:", response.data); // 디버깅
        alert("삭제 완료");
        this.$router.push("/faq/talklist");
      } catch (error) {
        console.error("삭제 중 에러:", error);
      }
    },
  },
  mounted() {
    const tno = this.$route.params.tno;
    if (tno) {
      this.getDetail(tno);
    } else {
      console.error("URL에 'tno' 파라미터가 누락되었습니다.");
    }
  },
};
</script>
<style>
.talkanswer_button_box{
  display: flex;
  margin-top: 1%;
  justify-content: end;
  margin: 15px 120px 10px 0;
}
.talk_button1 {
  margin-top: 1%;
  margin-left: 94.5%;
}
.notice_content1 {
  width: 90%;
  margin-left: 5%;
}
.accordion-header button {
  font-weight: bold;
  text-align: left;
}
/* 공지 전체 */
.bigbox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
/* 타이틀 */
.title {
  display: inline-flex;
  top: 30px;
  right: 16%;
  position: relative;
}
/* 전체 박스 */
.announce_body_box {
  width: 70%;
  border: 2.5px solid black;
  border-radius: 10px;
  padding: 15px;
}
.title_category_box {
  display: flex;
}
/* 제목 */
.talk_title {
  margin-left: 14%;
  margin-right: 2%;
  width: 40%;
}
/* 카테고리, 날짜 박스 */
.talk_info1 {
  display: flex;
  justify-content: space-between;
  margin: 25px 0 10px 0;
}
/* 카테고리 */
.talk_category1 {
  margin-left: 20%;
}
/* 날짜 */
.talk_date1 {
  margin-right: 20%;
}
</style>
