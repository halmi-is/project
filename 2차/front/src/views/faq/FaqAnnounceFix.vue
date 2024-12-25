<template>
  <div class="bigbox1">
    <hr />
    <div class="announce_body_box1">
      <div class="notice_container1">
        <div class="notice_content1">
          <p>공지 수정</p>
          <br />
          <!-- 제목 수정 -->
          <div class="form-floating talk_content1">
            <textarea
              type="text"
              class="form-control"
              id="titleTextarea"
              placeholder="제목"
              required
              style="height: 60px"
              v-model="announcement.title"
            ></textarea>
            <label for="titleTextarea">제목</label>
          </div>
          <br />
          <!-- 내용 수정 -->
          <div class="form-floating talk_content1">
            <textarea
              type="text"
              class="form-control"
              id="contentTextarea"
              placeholder="내용"
              required
              style="height: 100px"
              v-model="announcement.content"
            ></textarea>
            <label for="contentTextarea">내용</label>
          </div>
        </div>
        <div class="button-group1">
          <!-- 수정 저장 버튼 -->
          <button type="button" class="btn btn-primary me-2" @click="update">
            저장
          </button>
          <button type="button" class="btn btn-danger me-2" @click="remove">
            삭제
          </button>
          <!-- 돌아가기 버튼 -->
          <router-link :to="'/announcement'">
            <button type="button" class="btn btn-warning backbutton1">
              <i class="bi bi-arrow-return-left"></i>
            </button>
          </router-link>
        </div>
        <br />
      </div>
    </div>
  </div>
</template>

<script>
import AnnouncementService from "@/services/faq/AnnouncementService";

export default {
  data() {
    return {
      announcement: {
        ano: "", // 공지 ID
        title: "", // 제목
        content: "", // 내용
        createDate: "",
      },
    };
  },
  methods: {
    // 공지 상세 데이터 가져오기
    async getDetail(ano) {
      try {
        const response = await AnnouncementService.get(ano);
        this.announcement = response.data;
        console.log("공지 상세 데이터 로드 성공:", response.data);
      } catch (error) {
        console.error(`Requesting: /api/announcement/fix/${ano}`);
        console.error("공지사항 데이터 로드 중 에러:", error);
      }
    },
    // 공지 수정 데이터 저장
    async update() {
      try {
        let response = await AnnouncementService.update(
          this.announcement.ano,
          this.announcement
        );
        console.log("공지 수정 성공:", response.data);
        this.$router.push("/adminfaqannouncement");
      } catch (error) {
        console.log(error);
      }
    },
    async remove() {
      try {
        const response = await AnnouncementService.remove(this.announcement.ano);
        console.log("삭제 성공:", response.data); // 디버깅
        alert("삭제 완료");
        this.$router.push("/adminfaqannouncement");
      } catch (error) {
        console.error("삭제 실패:", error);
        alert("삭제 실패: 서버에 문제가 발생했습니다.");
      }
    },
  },
  mounted() {
    this.getDetail(this.$route.params.ano);
  },
};
</script>

<style>
/* 공지 전체 */
.bigbox1 {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
/* 전체 박스 */
.announce_body_box1 {
  width: 70%;
  border: 2.5px solid black;
  border-radius: 10px;
  padding: 15px;
}
.title1 {
  text-align: center;
  font-weight: bolder;
  font-size: 25px;
}
.content1 {
  padding: 5px 5px 5px 10px;
}
.button-group1 {
  display: flex;
  padding: 5px;
  justify-content: end;
}
.backbutton1 {
  position: relative;
}
</style>
