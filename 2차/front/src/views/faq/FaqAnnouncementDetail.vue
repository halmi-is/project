<template>
  <div class="bigbox">
    <hr />
    <div class="announce_body_box">
      <div class="notice_container">
        <div class="notice_content">
          <br /><br />
          <p class="title">{{ title }}</p>
          <hr />

          <p class="content">{{ content }}</p>
          <p>{{ createDate }}</p>
        </div>
        <!-- ano 값을 동적으로 설정 -->
        <div class="button-group23">
          <router-link :to="`/announcement/fix/${ano}`" v-if="userRole === 'ROLE_ADMIN'">
            <button type="button" class="btn btn-warning me-2">수정</button>
          </router-link>
          <router-link to="/announcement">
            <button type="button" class="btn btn-warning button">
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
      userRole:"",
      ano: "",
      title: "",
      content: "",
      createDate: "",
    };
  },
  methods: {
    async getDetail(ano) {
      try {

        let response = await AnnouncementService.get(ano);
        this.ano = response.data.ano;
        this.title = response.data.title;
        this.content = response.data.content;
      } catch (error) {
        console.error("공지사항 데이터 로드 중 에러:", error);
      }
    },
  },
  mounted() {
    const ano = this.$route.params.ano;
    if (ano) {
      this.getDetail(ano);
    } else {
      console.error("URL에 'ano' 파라미터가 누락되었습니다.");
    }
    const user= localStorage.getItem("user");
    if (user) {
      const parsedUser = JSON.parse(user);
      this.userRole = parsedUser.codeName;
    } else {
      console.error("No user data found in localStorage.");
    }
  },
};
</script>

<style>
/* 공지 전체 */
.bigbox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

/* 전체 박스 */
.announce_body_box {
  width: 70%;
  border: 2.5px solid black;
  border-radius: 10px;
  padding: 15px;
}

.title {
  text-align: center;
  font-weight: bolder;
  font-size: 25px;
}

.content {
  padding: 5px 5px 5px 10px;
}

.button-group23 {
  display: flex;
  padding: 5px;
  justify-content: end;
}
</style>
