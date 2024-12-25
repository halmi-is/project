<template>
  <div></div>
  <div class="container d-flex justify-content-center align-items-center">
    <!-- 버튼 그룹 박스 -->
    <div class="mt-3" id="main_button_group">
      <b-button-group size="lg" class="custom-button-group">
        <div>
          <b-button
            variant="outline-dark"
            class="custom-button"
            @click.prevent="handleClick"
          >
            <i class="bi bi-chat-square-dots custom-icon"></i>
            <br />1:1 문의
          </b-button>
        </div>

        <router-link :to="'/faq/list'">
          <b-button variant="outline-dark" class="custom-button">
            <i class="bi bi-receipt-cutoff custom-icon"></i><br />질문 게시판
          </b-button></router-link
        >

        <router-link :to="'/faq/payment'">
          <b-button variant="outline-dark" class="custom-button">
            <i class="bi bi-cash-coin custom-icon"></i><br />결제 방법
          </b-button>
        </router-link>

        <router-link :to="'/announcement'"
          ><b-button variant="outline-dark" class="custom-button">
            <i class="bi bi-megaphone custom-icon"></i><br />공지사항
          </b-button></router-link
        >
      </b-button-group>
    </div>
  </div>
  <!-- 검색창 박스 -->
  <div class="search_box">
    <form class="d-flex search_bar" method="get" action="/faq/list">
      <input
        class="form-control me-2 search_text_main"
        name="search"
        type="search"
        placeholder="궁금한 것을 물어보세요"
        aria-label="Search"
      />
      <button class="btn btn-outline-warning search_glass_main" type="submit">
        <i class="bi bi-search"></i>
      </button>
    </form>
  </div>
  <!-- 한 줄 도움말 목록 -->
  <br />
  <hr />
  <br />
  <div class="one-line-box">
    <h1 class="body-title">&nbsp;&nbsp;자주 찾는 질문</h1>
    <form name="oneLineHelp">
      <div
        id="carouselExampleControls"
        class="carousel slide"
        data-bs-ride="carousel"
      >
        <!-- 해시태그 버튼-->
        <div class="hashtag-nav" id="category">
          <button
            v-for="(item, index) in cards"
            :key="item.id"
            class="hashtag-button"
            :data-bs-target="'#carouselExampleControls'"
            :data-bs-slide-to="index"
          >
            <i :class="item.icon" id="hashtag-icon"></i> {{ item.name }}
          </button>
        </div>

        <!-- 해시태그 카드 모음 -->
        <div class="carousel-inner">
          <div
            class="carousel-item"
            v-for="(item, index) in cards"
            :key="item.id"
            :class="{ active: index === 0 }"
            data-bs-interval="999999"
          >
            <div class="grid-container">
              <div
                class="card"
                v-for="(question, qIndex) in item.questions"
                :key="`question-${item.id}-${qIndex}`"
              >
                <b-card>
                  <!-- 제목 -->
                  <b-card-title class="card-title">
                    <i :class="question.icon"></i> {{ question.title }}
                  </b-card-title>
                  <hr />
                  <!-- 질문 -->
                  <p
                    v-for="(text, tIndex) in question.texts"
                    :key="`text-${question.id}-${tIndex}`"
                    class="card-body"
                    data-bs-toggle="popover"
                    :data-bs-content="text.popbody"
                  >
                    - {{ text.text }}
                  </p>
                  <hr />
                  <div v-if="showPopover" class="popover" :style="popoverStyle">
                    <h3 class="popover-title">Popover Title</h3>
                    <div class="popover-content">I am popover content!</div>
                  </div>
                  <router-link
                    :to="{ path: 'faq/list', query: { search: question.link } }"
                    ><b-button variant="danger" class="card-button"
                      ><i class="bi bi-three-dots"></i>
                      <i class="bi bi-info-circle"></i></b-button
                  ></router-link>
                </b-card>
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import * as bootstrap from "bootstrap";
export default {
  methods: {
    handleClick(event) {
      if (this.user === null) {
        event.preventDefault(); // 기본 링크 이동 방지
        alert("로그인이 필요합니다. 로그인을 먼저 해주세요.");
        return;
      } else {
        this.$router.push("/faq/talk"); // 링크 이동
      }
    },
  },
  // 팝오버
  name: "FaqMain",
  mounted() {
    // Popover 초기화
    const popoverTriggerList = document.querySelectorAll(
      '[data-bs-toggle="popover"]'
    );
    popoverTriggerList.forEach((popoverTriggerEl) => {
      new bootstrap.Popover(popoverTriggerEl, {
        trigger: "hover", // 호버 시 표시
        delay: { show: 200, hide: 200 }, // 숨김 시 딜레이 추가
        placement: "right", // 팝오버 위치 (필요시 조정)
      });
    });
    const user = localStorage.getItem("user");
    if (user) {
      const parsedUser = JSON.parse(user);
      this.user = parsedUser;
      this.userEmail = parsedUser.email;
    } else {
      console.error("No user data found in localStorage.");
    }
  },

  data() {
    return {
      user: null,
      cards: [
        {
          id: "reservation",
          name: "예약 문의",
          icon: "bi bi-building-exclamation",
          questions: [
            {
              title: "예약 변경",
              icon: "bi bi-journal-richtext",
              link: "예약",
              texts: [
                {
                  text: "예약 변경은 출발 3일 전까지 가능합니다.",
                  popbody:
                    "예약 변경은 출발 3일 전에 가능합니다. 자세한 사항은 문의 바랍니다.",
                },
                {
                  text: "단순 변심 변경은 7일 전까지 가능합니다.",
                  popbody: "단순 변심에 의한 변경은 7일 전까지 가능합니다.",
                },
                {
                  text: "같은 날짜 다발성 예약은 불가합니다.",
                  popbody: "같은 날짜에 여러 예약은 불가능합니다.",
                },
              ],
            },
            {
              title: "취소 규정",
              icon: "bi bi-clipboard-check",
              link: "취소",
              texts: [
                {
                  text: "취소는 출발 5일 전까지 가능합니다.",
                  popbody:
                    "취소는 출발 5일 전까지 가능하며, 일부 수수료가 발생할 수 있습니다.",
                },
                {
                  text: "취소 후 재예약은 제한될 수 있습니다.",
                  popbody: "취소 후 동일 날짜 재예약은 제한될 수 있습니다.",
                },
                {
                  text: "수수료가 부과될 수 있습니다.",
                  popbody: "취소 시 수수료가 부과될 수 있습니다.",
                },
              ],
            },
            {
              title: "예약 확인 방법",
              icon: "bi bi-eye",
              link: "예약",
              texts: [
                {
                  text: "예약 확인은 이메일로 안내됩니다.",
                  popbody: "이메일을 통해 예약 정보를 확인하세요.",
                },
                {
                  text: "예약 정보는 마이페이지에서 확인 가능합니다.",
                  popbody:
                    "마이페이지에서 현재 예약 상태를 확인할 수 있습니다.",
                },
                {
                  text: "문의 시 예약번호를 준비해주세요.",
                  popbody: "고객센터 문의 시 예약번호를 반드시 준비해주세요.",
                },
              ],
            },
          ],
        },
        {
          id: "overseas",
          name: "해외 문의",
          icon: "bi bi-globe-americas",
          questions: [
            {
              title: "비자 발급",
              icon: "bi bi-flag",
              link: "비자",
              texts: [
                {
                  text: "비자 발급 조건은 국가별로 다릅니다.",
                  popbody: "각국의 비자 발급 조건을 확인하세요.",
                },
                {
                  text: "비자 발급 소요시간은 2주입니다.",
                  popbody: "비자 발급은 평균적으로 2주가 소요됩니다.",
                },
                {
                  text: "필요한 서류 목록을 확인하세요.",
                  popbody:
                    "비자 발급에 필요한 서류 목록은 담당 기관에 문의하세요.",
                },
              ],
            },
            {
              title: "여행자 보험",
              icon: "bi bi-shield",
              link: "보험",
              texts: [
                {
                  text: "여행자 보험을 꼭 가입해야 하나요?",
                  popbody:
                    "여행자 보험은 예기치 못한 상황에 대비하기 위해 필수적입니다.",
                },
                {
                  text: "보험료가 여행 일정에 따라 변할수도 있나요?",
                  popbody: "보험료는 여행 기간 및 목적지에 따라 상이합니다.",
                },
                {
                  text: "보험의 정확한 내용을 확인하고 싶어요.",
                  popbody: "보장 항목과 제한 사항을 반드시 확인하세요.",
                },
              ],
            },
          ],
        },
        {
          id: "payment-refund",
          name: "결제/환불",
          icon: "bi bi-credit-card",
          questions: [
            {
              title: "결제 수단",
              icon: "bi bi-wallet",
              link: "결제",
              texts: [
                {
                  text: "어떤 결제 수단을 사용할 수 있나요?",
                  popbody: "지원되는 결제 수단: 카드, 계좌이체, 간편결제 등.",
                },
                {
                  text: "결제 후 변경 가능한가요?",
                  popbody: "결제 후 변경은 불가능합니다.",
                },
                {
                  text: "해외 결제는 지원되나요?",
                  popbody:
                    "해외 결제는 지원되며, 환율 변동에 따라 금액이 달라질 수 있습니다.",
                },
              ],
            },
            {
              title: "환불 요청",
              icon: "bi bi-arrow-return-left",
              link: "환불",
              texts: [
                {
                  text: "환불 요청은 어떻게 하나요?",
                  popbody: "환불 요청은 고객센터나 마이페이지에서 가능합니다.",
                },
                {
                  text: "환불 처리 기간은 얼마나 걸리나요?",
                  popbody: "환불 처리는 7~10 영업일 소요될 수 있습니다.",
                },
                {
                  text: "부분 환불은 가능한가요?",
                  popbody: "부분 환불은 결제 수단에 따라 다를 수 있습니다.",
                },
              ],
            },
          ],
        },
        {
          id: "account-management",
          name: "계정 관리",
          icon: "bi bi-person",
          questions: [
            {
              title: "비밀번호 재설정",
              icon: "bi bi-shield-lock",
              link: "계정",
              texts: [
                {
                  text: "비밀번호를 잊었을 때 어떻게 하나요?",
                  popbody: "로그인 화면에서 비밀번호 찾기를 클릭하세요.",
                },
                {
                  text: "재설정 링크가 오지 않을 경우?",
                  popbody: "스팸 메일함을 확인하거나 고객센터에 문의하세요.",
                },
                {
                  text: "보안 절차는 어떻게 진행되나요?",
                  popbody: "보안 질문 확인 후 비밀번호를 재설정할 수 있습니다.",
                },
              ],
            },
          ],
        },
      ],
    };
  },
};
</script>

<style scoped>
.disabled-link {
  pointer-events: none; /* 클릭 차단 */
  opacity: 0.6; /* 비활성화된 스타일 */
  cursor: not-allowed; /* 마우스 커서 변경 */
}
.popover {
  background: white;
  border: 5px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  width: 200px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  z-index: 1000;
}
.popover-title {
  font-size: 25px;
  font-weight: bold;
  margin-bottom: 5px;
}
.popover-content {
  font-size: 14px;
}
/* 메인 아이콘 스타일 */
.custom-icon {
  font-size: 60px;
  color: #ffc107;
  margin-bottom: 0.5rem; /* 아이콘과 텍스트 간격 */
}
/* 버튼 그룹 스타일 */
#main_button_group {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  /* flex-direction: row; 버튼을 가로로 배치 */
}
/* 컨테이너 스타일 */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
}
/* 자주 찾는 질문 */
.body-title {
  font-size: 30px;
  font-weight: bold;
  margin-left: 15px;
}
/* 버튼 간격 설정 */
.custom-button-group .btn {
  margin: 0 10px;
  margin-bottom: 20px;
  width: 150px;
  height: 150px;
  text-align: center;
}
/* 버튼의 텍스트 스타일 */
.custom-button {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  border: 2px solid #ccc;
}
/* 버튼 호버 스타일 */
.custom-button:hover {
  background-color: #464444;
  border-color: #ccc;
  color: white;
}
/* 중앙 정렬 */
.custom-button-group {
  display: flex;
  justify-content: center;
  flex-wrap: wrap; /* 화면 크기에 따라 버튼 줄바꿈 */
}
/* 검색창 스타일 */
.search_box {
  justify-content: center;
  margin-top: 30px;
}
/* 검색바 */
.search_bar {
  width: 70%;
  height: 60px;
  margin-left: 17%;
}
/* 검색 창 */
.search_text_main {
  background-color: #ffffffb7;
  font-size: 17px;
  color: black;
  border: 2.5 px solid black;
  width: 89%;
}
.search_bar .search_text_main {
  border-top-left-radius: 50px;
  border-bottom-left-radius: 50px;
  border: 2.5px solid black;
}
.search_bar .search_glass_main {
  border-top-right-radius: 50px;
  border-bottom-right-radius: 50px;
  border: 2.5px solid black;
}
/* 검색창 돋보기 */
.search_glass {
  right: -0.5px;
}
/* 자주 찾는 질문 전체 박스 */
#category {
  background-color: white;
  border-radius: 10px;
  padding: 10px;
  margin-top: 20px;
  margin-bottom: 20px;
}
/* 해시태그 버튼 */
.hashtag-button {
  margin-right: 10px;
  padding: 5px 10px;
  border: 1px solid black;
  border-radius: 5px;
  background-color: white;
  color: #333;
  cursor: pointer;
  transition: background-color 0.3s ease;
  font-size: 19px;
}
/* 해시태그 아이콘 */
#hashtag-icon {
  color: #ffc107;
  font-size: 25px;
}
/* 해시태그 호버 */
.hashtag-button:hover {
  background-color: black;
  color: white;
}
/* 해시태그 하이라이트 */
.hashtag-button.active {
  background-color: black;
  color: white;
}
/* 카드 그리드 박스 */
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin: 20px 0;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
}
/* 카드 스타일 */
.card {
  border: 1px solid black !important;
  border-radius: 10px;
  padding: 5px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-sizing: border-box;
  height: 100%; /* 카드 높이를 고정 */
}
.card * {
  border: none !important;
}
/* 카드 타이틀 */
.card-title {
  font-size: 23px;
  font-weight: bolder;
  margin-bottom: 10px;
}
/* 카드 내용 */
.card-body {
  font-size: 15px;
  color: #666;
  text-align: start;
  margin: 5px 0;
  padding: 0;
  line-height: 1.5;
  flex: 1;
}
/* 카드 버튼 */
.card-button {
  background-color: #ffc107;
  border: 1px solid black !important;
  color: black;
}

.carousel-item {
  transition: none !important; /* 애니메이션 효과 제거 */
}
</style>
