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
          <p class="talk_big_title"> 1:1 문의</p>
          <br />
          <div class="notice_content1">
            <div class="accordion acco_box" id="talkAccordion">
              <div
                class="accordion-item"
                v-for="(talk, index) in talkList"
                :key="index"
              >
                <!-- 아코디언 헤더 -->
                <h2 class="accordion-header" :id="'heading-' + index">
                  <button
                    class="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    :data-bs-target="'#collapse-' + index"
                    :aria-expanded="false"
                    :aria-controls="'collapse-' + index"
                    selected
                  >
                    {{ talk.title }}
                  </button>
                </h2>
                <!-- 아코디언 내용 -->
                <div
                  :id="'collapse-' + index"
                  class="accordion-collapse collapse"
                  :aria-labelledby="'heading-' + index"
                  data-bs-parent="#talkAccordion"
                >
                  <div class="talk_info1">
                    <!-- 카테고리 -->
                    <div class="form-floating talk_category1">
                      <input
                        type="text"
                        class="form-control"
                        id="validationDefault01"
                        placeholder="카테고리"
                        readonly
                        v-model="talk.category"
                      />
                      <label for="validationDefault01">카테고리</label>
                    </div>
                    <!-- 작성일 -->
                    <div class="form-floating talk_date1">
                      <input
                        type="text"
                        class="form-control"
                        id="validationDefault01"
                        placeholder="작성일"
                        readonly
                        v-model="talk.createDate"
                      />
                      <label for="validationDefault01">작성일</label>
                    </div>
                  </div>
                  <div class="talk_content_box1">
                    <!-- 내용 -->
                    <div class="form-floating talk_content1">
                      <textarea
                        type="text"
                        class="form-control"
                        id="validationDefault02"
                        placeholder="질문"
                        readonly
                        style="height: 250px"
                        v-model="talk.content"
                      ></textarea>
                      <label for="validationDefault02">내용</label>
                    </div>
                    <!-- 답변 -->
                    <div class="form-floating talk_reply1">
                      <textarea
                        type="text"
                        class="form-control"
                        id="validationDefault02"
                        placeholder="답변"
                        readonly
                        style="height: 250px"
                        :value="talk.reply || '아직 답변이 없습니다.'"
                      ></textarea>
                      <label for="validationDefault02">답변</label>
                    </div>
                  </div>
                  <div>
                    
                    <router-link
                      :to="'/faq/talkanswer/' + talk.tno"
                      class="talk_button2"
                      v-if="userRole === 'ROLE_ADMIN'"
                    >
                      <button type="button" class="btn btn-warning">
                        <i class="bi bi-arrow-return-left">답변</i>
                      </button>
                    </router-link>
                  </div>
                  <br />
                </div>
              </div>
              <p v-if="talkList.length === 0">등록된 대화가 없습니다.</p>
            </div>
            <div class="talk_button1">
              <router-link :to="'/faq/talk'">
                <button type="button" class="btn btn-warning goto_button">
                  <i class="bi bi-arrow-return-left"></i>
                </button>
              </router-link>
            </div>
          </div>
          <br />
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
      userRole: "",
      talkList: [], // 전체 데이터를 저장할 리스트
      searchKeyword: "", // 검색어
      pageIndex: 1, // 현재 페이지
      recordCountPerPage: 10, // 한 페이지에 표시할 레코드 수
    };
  },
  methods: {
    async fetchTalks() {
      try {
        const response = await TalkService.getAll(
          this.searchKeyword,
          this.pageIndex - 1,
          this.recordCountPerPage
        );
        const { results } = response.data; // 서버에서 반환된 결과
        this.talkList = results.map((talk) => ({
          ...talk,
          reply: talk.reply || "아직 답변이 없습니다.", // 기본값 설정
        }));
      } catch (error) {
        console.error("문의 전체 조회 실패:", error);
      }
    },
  },
  mounted() {
    const user = localStorage.getItem("user");
    if (user) {
      const parsedUser = JSON.parse(user);
      this.userRole = parsedUser.codeName;
    } else {
      console.error("No user data found in localStorage.");
    }

    this.fetchTalks(); // 컴포넌트가 마운트될 때 데이터 로드
  },
};
</script>
<style>
.talk_button1 {
  margin-top: 1%;
  margin-left: 94.5%;
}
.talk_button2 {
  margin-top: 1%;
  margin-left: 91%;
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
  margin: 10px 0 10px 0;
}
/* 카테고리 */
.talk_category1 {
  width: 100%;
  margin: 5px 5px 10px 30px;
}
/* 날짜 */
.talk_date1 {
  width: 100%;
  margin: 5px 30px 10px 5px;
}

/* 내용, 답변 박스 */
.talk_content_box1 {
  display: flex;
  justify-content: space-between;
  margin: 10px 0 10px 0;
}
/* 내용 */
.talk_content1 {
  width: 100%;
  margin: 5px 5px 10px 30px;
}
/* 답변 */
.talk_reply1 {
  width: 100%;
  margin: 5px 30px 10px 5px;
}
</style>
