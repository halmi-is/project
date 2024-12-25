<template>
  <div>
    <!-- TODO: 캐러셀 -->
    <div
      id="carouselExampleAutoplaying"
      class="carousel slide"
      data-bs-ride="carousel"
      data-bs-interval="1500"
      style="margin-bottom: 20px"
    >
      <div class="carousel-inner">
        <!-- 슬라이드 1 -->
        <div class="carousel-item active">
          <img
            :src="require('@/assets/images/re4.jpg')"
            alt="Slide 1"
            class="d-block w-100 img-fluid rounded shadow"
          />
        </div>
        <!-- 슬라이드 2 -->
        <div class="carousel-item">
          <img
            :src="require('@/assets/images/re2.jpg')"
            alt="Slide 2"
            class="d-block w-100 img-fluid rounded shadow"
          />
        </div>
        <!-- 슬라이드 3 -->
        <div class="carousel-item">
          <img
            :src="require('@/assets/images/re3.jpg')"
            alt="Slide 3"
            class="d-block w-100 img-fluid rounded shadow"
          />
        </div>
      </div>
      <!-- 캐러셀 컨트롤러 (좌우 버튼) -->
      <button
        class="carousel-control-prev"
        type="button"
        data-bs-target="#carouselExampleAutoplaying"
        data-bs-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button
        class="carousel-control-next"
        type="button"
        data-bs-target="#carouselExampleAutoplaying"
        data-bs-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>

    <!-- TODO: 카드 -->
    <div class="row">
      <!-- 카드 반복 -->
      <div v-for="(data, index) in recommends" :key="index" class="col-3 mb-4">
        <div class="card custom-card">
          <img
            :src="data.imageUrl"
            class="card-img-top custom-card-img"
            alt="카드"
            @click="goToDetailPage(data)"
          />
          <div class="card-body custom-card-body">
            <h5 class="card-title text-warning">{{ data.tdName }}</h5>
            <p class="card-text text-light">
              {{ data.loc }}
              <br />
              {{ data.description }}
            </p>
            <!-- 수정 버튼 -->
            <div
              class="d-flex justify-content-between"
              v-if="userRole === 'ROLE_ADMIN'"
            >
              <router-link
                :to="'/recommendupdate/' + data.tdId"
                class="btn btn-dark btn-sm"
              >
                수정
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- TODO: 검색어 입력상자 -->
    <div class="input-group mb-3 mt-3" style="width: 300px; margin: 0 auto">
      <input
        type="text"
        class="form-control"
        placeholder="검색어"
        v-model="searchKeyword"
      />
      <button class="btn btn" type="button" @click="getrecommend">검색</button>
    </div>

    <!-- TODO: 페이지 번호 : 부트스트랩뷰(페이지) -->
    <div
      class="mt-3 text-center d-flex justify-content-between align-items-center"
    >
      <!-- 페이지네이션 -->
      <b-pagination
        v-model="pageIndex"
        :total-rows="totalCount"
        :per-page="recordCountPerPage"
        @click="getrecommend"
        style="margin-left: 42%"
        class="custom-pagination"
      ></b-pagination>

      <!-- 추가 버튼 -->
      <router-link
        to="/add-recommend"
        class="btn btn-warning btn-sm ml-3"
        v-if="userRole === 'ROLE_ADMIN'"
      >
        추가
      </router-link>
    </div>
  </div>
</template>

<script>
import RecommendService from "@/services/recommend/RecommendService";
export default {
  data() {
    return {
      pageIndex: 1,
      totalCount: 0,
      recordCountPerPage: 8,
      searchKeyword: "",
      recommends: [],
      carouselItems: [],
      userRole: "", // 유저 권한
    };
  },

  methods: {
    async getrecommend() {
      try {
        let response = await RecommendService.getAll(
          this.searchKeyword,
          this.pageIndex - 1,
          this.recordCountPerPage
        );
        const { results, totalCount } = response.data;
        console.log(response.data); // 디버깅
        this.recommends = results;
        this.totalCount = totalCount;
      } catch (error) {
        console.log(error);
      }
    },

    goToDetailPage(data) {
      this.$router.push({
        path: `/recommenddetail/${data.tdId}`, // 동적 경로에 데이터 삽입
      });
    },
  },

  mounted() {
    this.getrecommend();
    // 로컬스토리지에서 토큰 정보 가져오기
    const user = localStorage.getItem("user"); // 저장된 사용자 정보 가져오기
    if (user) {
      const parsedUser = JSON.parse(user); // JSON 문자열을 객체로 파싱
      this.userRole = parsedUser.codeName; // 권한 정보 저장
    } else {
      console.error("No user data found in localStorage.");
    }
  },
};
</script>

<style>
/* 카드 디자인 */
.custom-card {
  background-color: #000; /* 검은색 배경 */
  color: #ffc107; /* 텍스트 기본 색 노란색 */
  border-radius: 10px;
  transition: all 0.3s ease; /* 부드러운 전환 효과 */
  cursor: pointer; /* 마우스 포인터를 손 모양으로 변경 */
}

.custom-card:hover {
  transform: scale(1.05); /* 크기 확대 */
}

.custom-card-img {
  height: 200px;
  object-fit: cover;
}

.custom-card-body {
  padding: 15px;
}

/* 텍스트 색상 조정 */
.card-text {
  color: #ffffff; /* 본문 텍스트 흰색 */
}

/* 수정 버튼 */
.btn-dark {
  background-color: #ffc107; /* 노란색 배경 */
  color: #000; /* 버튼 텍스트 검정색 */
  border: 1px solid #ffc107; /* 테두리 노란색 */
  transition: all 0.3s ease;
}

.btn-dark:hover {
  background-color: #000; /* 호버 시 검은색 배경 */
  color: #ffc107; /* 호버 시 노란색 텍스트 */
}

/* 추가 버튼 */
.btn-warning {
  background-color: white; /* 노란색 배경 */
  color: #000; /* 텍스트 검정색 */
  border: 1px solid #000; /* 검은색 테두리 */
}

.btn-warning:hover {
  background-color: #000; /* 호버 시 검은색 배경 */
  color: #ffc107; /* 호버 시 노란 텍스트 */
  border: 1px solid #ffc107; /* 노란색 테두리 */
}

/* 페이지네이션 중앙 정렬 */
.text-center {
  text-align: center;
}

.d-flex.justify-content-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.ml-3 {
  margin-left: 1rem; /* 좌측 여백 추가 */
}

/* 캐러셀 디자인 */
.carousel-inner img {
  height: 400px; /* 캐러셀 이미지 크기 조정 */
  object-fit: cover; /* 이미지 비율 유지 */
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
  filter: invert(1); /* 화이트 컬러로 아이콘 */
}

.custom-pagination .page-link {
  background-color: white;
  color: black;
  border-color: white;
}

.custom-pagination .page-item.active .page-link {
  background-color: white;
  color: black;
  border-color: black;
}

#carouselExampleAutoplaying {
  border-radius: 10px; /* 둥근 모서리 */
  padding: 10px; /* 여백 추가 */
}
</style>
