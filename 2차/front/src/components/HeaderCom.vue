<template>
  <!-- 최상단 메뉴 -->
  <div class="hearder_big_box">
    <b-nav class="upper_menu">
      <div class="upper_menu_link">
        <b-nav-item v-if="!this.$store.state.loggedIn" href="/login"
          >로그인</b-nav-item
        >
        <b-nav-item v-if="!this.$store.state.loggedIn">|</b-nav-item>
        <b-nav-item v-if="!this.$store.state.loggedIn" href="/register"
          >회원가입</b-nav-item
        >
      
        <b-nav-item v-if="this.$store.state.loggedIn" href @click="logout"
          >로그아웃</b-nav-item
        >
        <b-nav-item>|</b-nav-item>
        <b-nav-item href="/faq">고객센터</b-nav-item>

        <b-nav-item>|</b-nav-item>
        <b-nav-item href="/cart">
          <i class="bi bi-cart"></i>
          <span class="badge bg-danger cart-count">{{ cartCount }}</span>
        </b-nav-item>

        <b-nav-item v-show="userRole === 'ROLE_ADMIN'"> |</b-nav-item>
        <b-nav-item v-show="userRole === 'ROLE_ADMIN'" href="/mainadmin"
          >관리자 페이지</b-nav-item
        >
      </div>
    </b-nav>
  </div>
  <div class="full-head">
    <div class="container-fluid">
      <img
        src="@/assets/logo3.png"
        id="logo"
        class="gpt-icon"
        href="/"
        onclick="location.href='/'"
      />
    </div>

    <div class="second_menu_box">
      <router-link to="/coupon" class="header_menu">
        <i class="bi bi-ticket"></i> 쿠폰
      </router-link>
      &nbsp;|&nbsp;
      <router-link to="/recommendgoogleview" class="header_menu">
        <i class="bi bi-geo-alt"></i> 여행지검색
      </router-link>
      &nbsp;|&nbsp;
      <router-link to="/recommend" class="header_menu">
        <i class="bi bi-star"></i> 추천여행지
      </router-link>
      &nbsp;|&nbsp;
      <router-link to="/review" class="header_menu">
        <i class="bi bi-pencil"></i> 리뷰게시판
      </router-link>
      &nbsp;|&nbsp;
      <router-link to="/mypage" class="header_menu">
        <i class="bi bi-person-plus"></i> 마이페이지
      </router-link>
    </div>
  </div>
</template>

<script scoped>
import MemberService from "@/services/auth/MemberService";
export default {
  data() {
    return {
      cartCount: 0,
      userRole: "", // 유저 권한
    };
  },
  methods: {
    logout() {
      MemberService.logout();
      this.$store.state.loggedIn = false;
      localStorage.removeItem("cartCount");
      window.location.href = "/login";
    },
  },
  mounted() {
    this.cartCount = parseInt(localStorage.getItem("cartCount")) || 0;
    this.intervalId = setInterval(() => {
      const updatedCount = parseInt(localStorage.getItem("cartCount")) || 0;
      if (this.cartCount !== updatedCount) {
        this.cartCount = updatedCount;
      }

      // 주기적으로 userRole 업데이트
      const user = localStorage.getItem("user");
      if (user) {
        const parsedUser = JSON.parse(user);
        if (this.userRole !== parsedUser.codeName) {
          this.userRole = parsedUser.codeName;
        }
      }
    }, 200);
  },
  beforeUnmount() {
    clearInterval(this.intervalId);
  },
};
</script>

<style>
.container-fluid {
  display: flex;
  align-items: center; /* 세로 중앙 정렬 */
  justify-content: center; /* 가로 중앙 정렬 */
  height: 180px; /* 전체 컨테이너 높이 */
  position: relative; /* 위치 설정 */
}

.gpt-icon {
  max-width: 80%;
  height: auto;
  position: absolute;
  top: 50%;
  left: 50%;
  height: 80%;
  width: 45%;
  transform: translate(-50%, -50%); /* 정확히 중앙으로 이동 */
}

.second_menu_box {
  font-size: 18px;
  font-family: hanna;
  text-align: center;
  margin: 10px 0 0 0;
}

.header_menu {
  gap: 5px; /* 아이콘과 텍스트 사이 간격 */
  text-decoration: none; /* 텍스트 밑줄 제거 */
  color: inherit; /* 부모 요소의 색상 유지 */
}
.header_menu i {
  font-size: 22px;
}
.header_menu:hover {
  color: #ffc107; /* 호버 시 색상 변경 */
  transform: scale(1.05); /* 약간 확대 효과 */
}
.nav-link {
  text-decoration: none !important;
  color: inherit !important;
}
.nav-link:hover {
  text-decoration: none !important;
  color: #ffeb33 !important;
}

/* 최상단 메뉴 */
.upper_menu {
  background-color: #000000;
}
.upper_menu_link {
  display: inline-flex;
  font-size: 11px;
  margin-left: 67%;
}
.upper_menu_link .nav-link {
  color: #ffffff !important;
}
.upper_menu_link .nav-link:hover {
  color: #ffeb33 !important;
}
.upper_menu_link .nav-item {
  margin-right: -25px;
}
.badge {
  font-size: 1rem;
  padding: 0.4rem;
}
.cart-count {
  font-size: 0.9rem;
}
</style>
