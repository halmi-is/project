<template>
  <!--완성 최종 커밋부탁 -->
  <div class="container mt-5">
    <div class="content-wrapper">
      <!-- 텍스트 영역 -->
      <div class="text-area">
        <h1 class="text-title">{{ tdName }}</h1>
        <p class="text-description">{{ description }}</p>
        <p class="text-location">위치: {{ loc }}</p>
      </div>

      <!-- 이미지 영역 -->
      <div class="image-area">
        <img :src="imageUrl" alt="Detail Image" class="detail-image" />
      </div>
    </div>
  </div>

  <div class="menu-container">
    <!-- <h2 class="menu-title" style="margin-top: 150px; margin-bottom: 135px">
      {{ menuTitle }}
    </h2> -->
    <div class="menu-grid">
      <!-- 메뉴 카드 -->
      <div v-for="menu in menuItems" :key="menu.MENU_ID" class="menu-card">
        <img
          :src="require(`@/assets/images/${menu.MENU_IMAGE_URL}`)"
          :alt="menu.MENU_NAME"
          class="menu-image"
        />
      </div>
    </div>
    <br />
    <hr />
    <!-- 절취선 -->

    <!-- <h2 class="menu-title2">{{ menuTitle2 }}</h2> -->
    <br />
    <!-- 맵 이미지 영역 -->
    <div
      v-for="menu in menuItems2"
      :key="menu.MENU_ID2"
      class="additional-image-area"
    >
      <img
        :src="require(`@/assets/images/${menu.MENU_MAP}`)"
        alt="Additional Detail"
        class="additional-image"
        @click="navigateToRecommendMap(menu)"
      />
    </div>
    <br />
    <hr />
    <br />

    <h3 style="font-weight: bold; margin-right: 700px; margin-bottom: 20px">
      댓글 리뷰
    </h3>
    <div class="review-list">
      <!-- 반복문으로 댓글 항목 렌더링 -->
      <div
        v-for="(data, index) in comments"
        :key="index"
        class="review-item mb-4"
        style="
          border-bottom: 1px solid #ddd;
          padding: 16px;
          background-color: #f9f9f9;
          border-radius: 8px;
        "
      >
        <div
          class="review-header"
          style="
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
          "
        >
          <div>
            <p
              class="review-user"
              style="
                margin: 0;
                font-weight: bold;
                color: #ffc107;
                font-size: 1.1em;
                text-transform: capitalize;
              "
            >
              {{ data.email }}
            </p>
            <p
              class="review-location"
              style="
                margin: 4px 0 0;
                font-size: 1em;
                color: #333;
                font-weight: bold;
              "
            >
              {{ data.commentLoc }}
            </p>

            <!-- 별점 디자인 추가 -->
            <div class="star-rating" style="margin-top: 8px">
              <span
                v-for="n in 5"
                :key="n"
                class="star"
                :style="{ color: n <= data.rating ? '#ffc107' : '#ddd' }"
              >
                ★
              </span>
            </div>
          </div>

          <span
            class="review-time"
            style="font-size: 0.85em; color: #777; align-self: flex-start"
          >
            {{ formatDate(data.createdAt) }}
          </span>
        </div>

        <div
          class="review-content"
          style="margin-top: 16px; padding-top: 8px; border-top: 1px solid #ddd"
        >
          <p
            class="review-text"
            style="color: #333; font-size: 1em; line-height: 1.6; margin: 0"
          >
            {{ data.commentText }}
          </p>

          <div
            class="review-actions"
            style="margin-top: 16px; text-align: right"
          >
            <router-link :to="'/recommendcomupdate/' + data.comId">
              <button
                v-if="data.email === userEmail || userRole === 'ROLE_ADMIN'"
                class="btn btn-outline-warning btn-sm"
              >
                관리
              </button>
            </router-link>
          </div>
        </div>
      </div>

      <!-- 페이지네이션 -->
      <div class="d-flex justify-content-center mt-3">
        <b-pagination
          v-model="pageIndex"
          :total-rows="totalCount"
          :per-page="recordCountPerPage"
          @click="getComments"
          style="
            --bs-pagination-active-bg: #ffc107; /* 활성화된 숫자 배경색 (Bootstrap Warning 색상) */
            --bs-pagination-active-color: white; /* 활성화된 숫자 텍스트 색상 */
            --bs-pagination-hover-bg: #ffc107; /* 호버된 숫자 배경색 */
            --bs-pagination-hover-color: white; /* 호버된 숫자 텍스트 색상 */
            --bs-pagination-link-bg: white; /* 화살표와 비활성 숫자 배경색 */
            --bs-pagination-link-color: white; /* 화살표와 비활성 숫자 텍스트 색상 */
            --bs-pagination-link-hover-bg: white; /* 화살표 호버 배경색 */
            --bs-pagination-link-hover-color: white; /* 화살표 호버 텍스트 색상 */
          "
        ></b-pagination>
      </div>

      <!-- 댓글 쓰기 버튼 -->
      <div style="text-align: right; margin-top: 16px">
        <a href="/recommendcomadd" class="btn btn-warning btn-sm">댓글 쓰기</a>
      </div>

      <!-- 검색어 입력상자 -->
      <div style="margin-top: 16px">
        <div class="input-group">
          <input
            type="text"
            class="form-control"
            placeholder="지역 검색"
            v-model="searchKeyword"
          />
          <button
            class="btn btn-outline-secondary btn-sm"
            type="button"
            @click="getComments"
          >
            검색
          </button>
        </div>
      </div>
    </div>

    <!-- 여기까지 -->
  </div>
</template>

<script>
import CommentsService from "@/services/recommend/CommentsService";
import RecommendService from "@/services/recommend/RecommendService";
export default {
  data() {
    return {
      comId: "",
      tdName: "",
      imageUrl: "",
      description: "",
      loc: "",
      commentText: "",
      createdAt: "",
      commentLoc: "",
      email: "",
      comments: [],
      pageIndex: 1, //현재페이지번호
      totalCount: 0, // 전체개수
      recordCountPerPage: 10, //화면에 보일개수
      searchKeyword: "",
      menuItems: [],
      menuItems2: [],
      menuItems3: [],
      menuTitle: "", // 메뉴 데이터를 저장할 배열
      // menuTitle2: "",
      isCollapsed: false,

      userEmail: "",
    };
  },

  mounted() {
    const tdId = this.$route.params.tdId;
    console.log("Received tdId:", tdId);
    this.fetchDetailData(tdId);
    this.getComments();

    const user = localStorage.getItem("user"); // 저장된 사용자 정보 가져오기
    if (user) {
      const parsedUser = JSON.parse(user); // JSON 문자열을 객체로 파싱
      this.userRole = parsedUser.codeName; // 권한 정보 저장
      this.userEmail = parsedUser.email; // 이메일 정보 저장
    } else {
      console.error("No user data found in localStorage.");
    }
  },

  methods: {
    formatDate(date) {
      const options = { year: "numeric", month: "2-digit", day: "2-digit" };
      return new Date(date).toLocaleDateString("ko-KR", options);
    },
    async fetchDetailData(tdId) {
      try {
        const response = await RecommendService.get(tdId);
        const data = response.data;

        this.tdName = data.tdName;
        this.imageUrl = data.imageUrl;
        this.description = data.description;
        this.loc = data.loc;

        console.log("Fetched data:", data);

        // 메뉴 데이터 하드코딩
        if (tdId == 11) {
          // this.menuTitle = "대표 메뉴"; // tdId 7에 맞는 메뉴 제목
          // this.menuTitle2 = "찾아오시는 길";
          this.menuItems = [
            {
              MENU_ID: 1,
              // MENU_NAME: "함안 대평늪",
              MENU_DETAIL: "호수",
              // MENU_PRICE: "3인분 xxx,xxx원",
              MENU_IMAGE_URL: "홋카이도2.jpg",
            },
            {
              MENU_ID: 2,
              // MENU_NAME: "고려동 유적지",
              MENU_DETAIL: "예쁜꽃",
              // MENU_PRICE: "xxx,xx원",
              MENU_IMAGE_URL: "홋카이도3.jpg",
            },
            {
              MENU_ID: 2,
              // MENU_NAME: "말이산 고분군",
              MENU_DETAIL: "오래된 목조품",
              // MENU_PRICE: "3인분 xxx,xx원",
              MENU_IMAGE_URL: "홋카이도4.jpg",
            },
          ];
          this.menuItems2 = [{ MENU_ID2: 1, MENU_MAP: "홋카이도5.jpg" }];
          this.menuItems3 = [
            {
              MENU_ID3: `부산역에서 KTX 또는 SRT를 타고 진주역으로 가서, 거기서 버스나 택시로 함안군으로 이동하면 됩니다.




                `,
            },
          ];
        } else if (tdId == 12) {
          this.menuTitle = "병신의 가볼만한 곳";
          this.menuItems = [
            {
              MENU_ID: 3,
              // MENU_NAME: "청풍문화재단지",
              // MENU_DETAIL:
              //   "조선 시대의 건축물과 생활상을 엿볼 수 있는 역사적인 장소",
              // MENU_PRICE: "3,500짯(640ml)",
              MENU_IMAGE_URL: "니스2.png",
            },
            {
              MENU_ID: 4,
              // MENU_NAME: "제천 청풍호반 케이블카",
              // MENU_DETAIL: "청풍호를 한눈에 볼 수 있는 케이블카",
              // MENU_PRICE: "15,000짯",
              MENU_IMAGE_URL: "니스3.jpg",
            },
            {
              MENU_ID: 2,
              // MENU_NAME: "의림지솔밭공원",
              // MENU_DETAIL: "의림지 주변에 조성된 솔밭공원에",
              // MENU_PRICE: "3,000짯(330ml)",
              MENU_IMAGE_URL: "니스4.jpg",
            },
          ];
          this.menuItems2 = [{ MENU_ID2: 1, MENU_MAP: "니스5.jpg" }];

          this.menuItems3 = [
            {
              MENU_ID3: `오시는 길: 버스: 제천 시내에서 의림지행 버스를 타고 약 20분 정도 소요됩니다.
자가용: 제천시청에서 의림지까지 약 10분 거리에 있습니다. 네비게이션에 "의림지"를 입력하면 됩니다.
기차: 제천역에서 택시나 버스를 타고 약 10분 거리에 의림지가 있습니다. 의림지는 공공장소로, 특별한 휴무일은 없습니다. 
연중무휴로 개방되며, 방문 시간에 제한은 없습니다. 다만, 특정 시설(예: 전시관, 카페 등)은
 운영 시간이나 휴무일이 있을 수 있으므로 해당 시설의 정보를 확인하는 것이 좋습니다.
                `,
            },
          ];
        } else if (tdId == 13) {
          // this.menuTitle = "ㅇㅇ";
          this.menuItems = [
            {
              MENU_ID: 5,

              MENU_IMAGE_URL: "광안리2.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "광안리3.jpeg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "광안리1.jpg",
            },
          ];
          this.menuItems2 = [{ MENU_ID2: 1, MENU_MAP: "광안리5.jpg" }];
        } else if (tdId == 14) {
          // this.menuTitle = "ㅇㅇ";
          this.menuItems = [
            {
              MENU_ID: 5,

              MENU_IMAGE_URL: "제주2.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "제주3.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "제주4.jpg",
            },
          ];
          this.menuItems2 = [{ MENU_ID2: 1, MENU_MAP: "제주5.png" }];
        } else if (tdId == 15) {
          // this.menuTitle = "ㅇㅇ";
          this.menuItems = [
            {
              MENU_ID: 5,

              MENU_IMAGE_URL: "속초2.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "속초3.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "속초4.jpg",
            },
          ];
          this.menuItems2 = [{ MENU_ID2: 1, MENU_MAP: "속초5.png" }];
        } else if (tdId == 16) {
          // this.menuTitle = "ㅇㅇ";
          this.menuItems = [
            {
              MENU_ID: 5,

              MENU_IMAGE_URL: "시드니2.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "시드니3.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "시드니4.jpg",
            },
          ];
          this.menuItems2 = [{ MENU_ID2: 1, MENU_MAP: "시드니5.png" }];
        } else if (tdId == 17) {
          // this.menuTitle = "ㅇㅇ";
          this.menuItems = [
            {
              MENU_ID: 5,

              MENU_IMAGE_URL: "다낭2.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "다낭3.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "다낭4.jpg",
            },
          ];
          this.menuItems2 = [{ MENU_ID2: 1, MENU_MAP: "다낭5.png" }];
        } else if (tdId == 18) {
          // this.menuTitle = "ㅇㅇ";
          this.menuItems = [
            {
              MENU_ID: 5,

              MENU_IMAGE_URL: "오타루2.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "오타루3.jpg",
            },
            {
              MENU_ID: 6,

              MENU_IMAGE_URL: "오타루4.jpg",
            },
          ];
          this.menuItems2 = [{ MENU_ID2: 1, MENU_MAP: "오타루5.png" }];
        }

        // 추가적인 tdId에 대한 조건을 계속 추가할 수 있음
      } catch (error) {
        console.error("Error fetching detail data:", error);
        // 에러 처리 로직 추가 (예: 에러 메시지 표시)
      }
    },
    toggleCollapse() {
      this.isCollapsed = !this.isCollapsed; // 상태 토글
    },

    async getComments() {
      try {
        let response = await CommentsService.getAll(
          this.searchKeyword,
          this.pageIndex - 1,
          this.recordCountPerPage
        );
        // TODO: 백엔드 전송되는 것 : results(배열), totalCount(총개수)
        const { results, totalCount } = response.data;
        console.log(response.data); // 디버깅
        this.comments = results;
        this.totalCount = totalCount;
      } catch (error) {
        console.log(error);
      }
    },

    navigateToRecommendMap(menu) {
      const tdId = this.$route.params.tdId; // 구글 api 연동할tdId 값 가져오기
      // tdId 값을 기준으로 경로 다르게 설정
      if (tdId == 11) {
        // tdId가 7일 때
        if (menu.MENU_ID2 === 1) {
          this.$router.push({
            path: "/recommendmap",
            query: { menuId: menu.MENU_ID2 },
          });
        } else if (menu.MENU_ID2 === 2) {
          this.$router.push({
            path: "/recommendmap",
            query: { menuId: menu.MENU_ID2 },
          });
        }
      } else if (tdId == 12) {
        // tdId가 8일 때
        if (menu.MENU_ID2 === 1) {
          this.$router.push({
            path: "/recommendmap2",
            query: { menuId: menu.MENU_ID2 },
          });
        } else if (menu.MENU_ID2 === 2) {
          this.$router.push({
            path: "/recommendmap2",
            query: { menuId: menu.MENU_ID2 },
          });
        }
      } else if (tdId == 13) {
        if (menu.MENU_ID2 === 1) {
          this.$router.push({
            path: "/recommendmap3",
            query: { menuId: menu.MENU_ID2 },
          });
        } else if (menu.MENU_ID2 === 2) {
          this.$router.push({
            path: "/recommendmap3",
            query: { menuId: menu.MENU_ID2 },
          });
        }
      } else if (tdId == 14) {
        if (menu.MENU_ID2 === 1) {
          this.$router.push({
            path: "/recommendmap4",
            query: { menuId: menu.MENU_ID2 },
          });
        } else if (menu.MENU_ID2 === 2) {
          this.$router.push({
            path: "/recommendmap4",
            query: { menuId: menu.MENU_ID2 },
          });
        }
      } else if (tdId == 15) {
        /* ㅇㅇ */
        if (menu.MENU_ID2 === 1) {
          this.$router.push({
            path: "/recommendmap5",
            query: { menuId: menu.MENU_ID2 },
          });
        } else if (menu.MENU_ID2 === 2) {
          this.$router.push({
            path: "/recommendmap5",
            query: { menuId: menu.MENU_ID2 },
          });
        }
      } else if (tdId == 16) {
        /* ㅇㅇ */
        if (menu.MENU_ID2 === 1) {
          this.$router.push({
            path: "/recommendmap6",
            query: { menuId: menu.MENU_ID2 },
          });
        } else if (menu.MENU_ID2 === 2) {
          this.$router.push({
            path: "/recommendmap6",
            query: { menuId: menu.MENU_ID2 },
          });
        }
      } else if (tdId == 17) {
        /* ㅇㅇ */
        if (menu.MENU_ID2 === 1) {
          this.$router.push({
            path: "/recommendmap7",
            query: { menuId: menu.MENU_ID2 },
          });
        } else if (menu.MENU_ID2 === 2) {
          this.$router.push({
            path: "/recommendmap7",
            query: { menuId: menu.MENU_ID2 },
          });
        }
      } else if (tdId == 18) {
        /* ㅇㅇasd */
        if (menu.MENU_ID2 === 1) {
          this.$router.push({
            path: "/recommendmap8",
            query: { menuId: menu.MENU_ID2 },
          });
        } else if (menu.MENU_ID2 === 2) {
          this.$router.push({
            path: "/recommendmap8",
            query: { menuId: menu.MENU_ID2 },
          });
        }
      }
      /* ㅇㅇ */
    },
  },
};
</script>

<style scoped>
/* 전체 컨테이너 */
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;

  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 콘텐츠 래퍼: 그리드 레이아웃 */
.content-wrapper {
  display: grid;
  grid-template-columns: 1fr 1.5fr; /* 텍스트 1, 이미지 1.5 비율 */
  gap: 20px;
  align-items: center; /* 세로 가운데 정렬 */
}

/* 텍스트 영역 */
.text-area {
  text-align: left; /* 왼쪽 정렬 */
}

.text-title {
  font-size: 24px;
  font-weight: bold;
  color: #000; /* 검정색 */
  margin-bottom: 10px;
}

.text-description,
.text-location {
  font-size: 16px;
  color: #000; /* 검정색 */
  margin-bottom: 10px;
}

/* 이미지 영역 */
.image-area {
  border-radius: 10px; /* 둥근 테두리 */

  display: flex;
  justify-content: center; /* 이미지 가운데 정렬 */
  align-items: center;
}

.detail-image {
  width: 100%;
  max-width: 100%; /* 부모에 맞추기 */
  height: auto; /* 비율 유지 */
  border-radius: 10px; /* 이미지 둥근 모서리 */
  object-fit: cover; /* 비율 맞추기 */
}

@media (max-width: 768px) {
  .content-wrapper {
    grid-template-columns: 1fr; /* 한 열로 배치 */
    gap: 10px;
  }

  .image-area {
    padding: 5px; /* 패딩 축소 */
  }
}

/* 전체 컨테이너 */
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;

  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* 콘텐츠 래퍼: 그리드 레이아웃 */
.content-wrapper {
  display: grid;
  grid-template-columns: 1fr 1.5fr; /* 텍스트 1, 이미지 1.5 비율 */
  gap: 20px;
  align-items: center; /* 세로 가운데 정렬 */
}

.text-area {
  text-align: left;
}

.text-title {
  font-size: 24px;
  font-weight: bold;
  color: #000;
  margin-bottom: 10px;
}

.text-description,
.text-location {
  font-size: 16px;
  color: #000;
  margin-bottom: 10px;
}

.image-area {
  background-color: #fff;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.detail-image {
  width: 100%;
  max-width: 100%;
  height: auto;
  border-radius: 10px;
  object-fit: cover;
}

@media (max-width: 768px) {
  .content-wrapper {
    grid-template-columns: 1fr; /* 한 열로 배치 */
    gap: 10px;
  }

  .image-area {
    padding: 5px;
  }
}

/* 전체 컨테이너 스타일 */
.menu-container {
  margin: 20px auto;
  max-width: 900px;
  text-align: center;
}

/* 제목 스타일 */
.menu-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

/* 메뉴 그리드 레이아웃 */
.menu-grid {
  display: flex;
  gap: 20px;
  justify-content: center;
  flex-wrap: wrap;
}

/* 개별 메뉴 카드 */
.menu-card {
  width: 250px;
  background-color: #fff;

  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.menu-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
}

/* 이미지 스타일 */
.menu-image {
  width: 100%;
  height: 160px;
  object-fit: cover;
}

.menu-description {
  padding: 10px;
  text-align: left;
}

.menu-name {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.menu-detail {
  font-size: 14px;
  color: black;
  font-weight: bold;
  margin-bottom: 5px;
}

.menu-price {
  font-size: 16px;
  font-weight: bold;
  color: #000;
}

.paragraph-wrapper {
  position: relative;
  padding: 1rem;
  margin-bottom: 2rem;
}

.paragraph-wrapper::before {
  content: "";
  position: absolute;
  bottom: -1rem;
  left: 10%;
  width: 80%;
  height: 1px;

  box-shadow: 0 4px 8px rgba(255, 193, 7, 0.5); /* 그림자 효과 */
}

/* 부모와 동일한 가로 크기를 가지는 추가 이미지 영역 */
.additional-image-area {
  width: 90%; /* 기본적으로 부모 너비를 채움 */
  max-width: 1200px; /* 최대 크기를 설정 (화면에 따라 조정 가능) */
  margin: 0 auto; /* 가운데 정렬 */
  text-align: center;
  position: relative;
  padding-top: 56.25%; /* 16:9 비율 유지 */
  border-radius: 10px; /* 둥근 모서리 */
  overflow: hidden; /* 이미지가 부모 영역을 초과하지 않도록 */
}

/* 추가 이미지 */
.additional-image {
  position: absolute; /* 부모 영역 내에서 위치 설정 */
  top: 0;
  left: 0;
  width: 100%; /* 부모의 가로 크기에 맞춤 */
  height: 100%; /* 부모의 높이에 맞춤 */
  object-fit: cover; /* 비율 유지하며 부모 영역을 완전히 채움 */
  border-radius: 10px; /* 이미지 둥근 모서리 */
}

.menu-title {
  font-family: "Garamond", serif; /* 멋있는 글씨체 예시 */
  font-size: 36px; /* 글씨 크기 */
  font-weight: bold; /* 글씨 두껍게 */
  color: #333; /* 글씨 색상 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); /* 텍스트 그림자 */
}

.text-title {
  font-family: "Arial", sans-serif; /* 기본 시스템 글꼴 */
  font-size: 30px; /* 글씨 크기 */
  font-weight: bold; /* 두껍게 */
  color: #2c3e50; /* 글씨 색상 */
  text-align: center; /* 가운데 정렬 */
  text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); /* 텍스트 그림자 */
  letter-spacing: 2px; /* 글자 간격 */
  transition: transform 0.3s ease-in-out; /* 마우스 오버 애니메이션 */
}

.text-title:hover {
  transform: scale(1.1); /* 마우스를 올렸을 때 크기 확대 */
}

.text-area {
  text-align: left; /* 텍스트를 좌측 정렬 */
  margin-left: 0; /* 왼쪽 여백을 0으로 설정 (필요시 여백 조정) */
}

.text-title {
  font-family: "Arial", sans-serif;
  font-size: 30px;
  font-weight: bold;
  color: #2c3e50;
  text-align: left; /* 텍스트 제목을 왼쪽 정렬 */
  text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
  letter-spacing: 2px;
  transition: transform 0.3s ease-in-out;
  margin-bottom: 10px; /* 하단 여백 추가 */
}

.text-description,
.text-location {
  font-size: 16px;
  color: #000;
  margin-bottom: 10px;
  text-align: left; /* 설명 텍스트를 왼쪽 정렬 */
}

/* 개별 메뉴 카드 */
.menu-card {
  width: 250px;
  background-color: #fff;

  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* 카드 자체의 확대 애니메이션 */
}

/* 카드에 호버 효과 */
.menu-card:hover {
  transform: scale(1.3); /* 카드를 5% 확대 */
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3); /* 카드 호버 시 그림자 강하게 */
}

/* 아코디언 전체 스타일 */
.accordion {
  max-width: 810px; /* 아코디언의 최대 가로 크기 설정 (원하는 크기로 수정) */
  margin: 0 auto; /* 중앙 정렬 */
}

/* 아코디언 헤더 버튼 스타일 */
.accordion-button {
  background-color: #ffc107; /* 노란색 배경 */
  color: #fff; /* 텍스트 색상 흰색 */
  border: 1px solid #ffc107; /* 테두리 노란색 */
  font-weight: bold; /* 글씨 굵게 */
}

/* 아코디언 버튼이 활성화될 때 색상 */
.accordion-button:focus {
  box-shadow: none; /* 포커스 상태에서 그림자 없애기 */
}

/* 아코디언 내용 영역 */
.accordion-collapse {
  background-color: #fff3cd; /* 밝은 노란색 배경 */
}

.accordion-body {
  color: #856404; /* 어두운 노란색 텍스트 */
}

/* 아코디언 내용 열릴 때의 스타일 */
.accordion-button:not(.collapsed) {
  background-color: #e0a800; /* 열렸을 때 조금 더 진한 노란색 배경 */
  color: #fff; /* 텍스트 색상 흰색 */
}

pre {
  white-space: pre-line; /* 여러 줄을 그대로 유지하고, 공백도 처리 */
  margin: 0; /* 불필요한 여백 제거 */
}

/* 이미지 오버 시 마우스 커서 모양 변경 */
.additional-image:hover {
  cursor: pointer; /* 마우스를 이미지 위에 올리면 손 모양 커서로 변경 */
}

/* 전체 댓글 섹션 스타일 */
.review-section {
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2); /* 더 부드러운 그림자 효과 */
  font-family: Arial, sans-serif; /* 깔끔한 폰트 */
}

/* 댓글 리스트 항목 스타일 */
.review-item {
  background-color: #fff; /* 카드 배경 흰색 */

  border-radius: 8px;
  margin-bottom: 15px;
  padding: 15px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

/* 댓글 헤더 (사용자 정보 및 시간) */
.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.review-user {
  color: #f57c00; /* 짙은 주황색으로 강조 */
  font-weight: bold;
  margin: 0;
}

.review-location {
  color: #fbc02d; /* 노란색 */
  font-size: 1.5rem;
  margin-top: 5px;
}

.review-time {
  color: #9e9e9e; /* 회색으로 시간 표시 */
  font-size: 0.85rem;
}

/* 댓글 내용 */
.review-content {
  margin-top: 10px;
}

.review-text {
  color: #212121; /* 텍스트 기본 색상 */
  font-size: 1.2rem;
  line-height: 1.5;
}

/* 버튼 스타일 */
.action-btn {
  background-color: #f5b506; /* 밝은 노란색 */

  border: none;
  padding: 8px 15px;
  font-size: 0.9rem;
  font-weight: bold;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.action-btn:hover {
  background-color: #f57f17; /* 진한 노란색 */
  color: white;
}

/* 댓글 쓰기 버튼 */
.review-write-button {
  text-align: right;
  margin-top: 20px;
}
.star-rating {
  display: inline-block;
}

.star {
  font-size: 1.5em; /* 별의 크기 조정 */
  margin-right: 4px;
  cursor: pointer; /* 커서를 포인터로 변경 */
}

.star:hover {
  color: #ffdd33; /* 호버 시 색상 변경 */
}
</style>
