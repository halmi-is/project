<template>
    <div class="container">
          <!-- 관리자 강조 박스들 -->
  <div class="container text-center admin-dashboard">
    <div class="row justify-content-center mt-4">
      <!-- 공지사항 박스 -->
      <div class="col-md-3">
        <div class="box-container">
          <router-link to="/adminfaqannouncement" class="custom-link">
            <div class="box">
              <i class="bi bi-clipboard-check box-icon"></i>
              <h3 class="box-title">공지사항(ADMIN)</h3>
             
            </div>
          </router-link>
        </div>
      </div>

      <!-- 자주 묻는 질문 박스 -->
      <div class="col-md-3">
        <div class="box-container">
          <router-link to="/adminfaqquestionlist" class="custom-link">
            <div class="box">
              <i class="bi bi-question-circle box-icon"></i>
              <h3 class="box-title">자주 묻는 질문(ADMIN)</h3>
          
            </div>
          </router-link>
        </div>
      </div>

      <!-- 1:1 문의 박스 -->
      <div class="col-md-3">
        <div class="box-container">
          <router-link to="/faq/talklist" class="custom-link">
            <div class="box">
              <i class="bi bi-chat-dots box-icon"></i>
              <h3 class="box-title">1:1 문의(ADMIN)</h3>

            </div>
          </router-link>
        </div>
      </div>
    </div>
  </div>

<!-- 여기까지 탭 -->

    <div class="bigbox">
      <hr />
      <div class="announce_body_box">
        <div class="notice_container">
          <div class="notice_search">
            <form class="search_input" @submit.prevent="searchAnnouncement">
              <router-link :to="'/faq/list'" class="custom-link">
                <p class="ano_top_title">공지사항(ADMIN)</p>
              </router-link>
              <div class="input_box typing form-group search_bar_announce">
                <input
                  placeholder="제목, 내용"
                  v-model="searchKeyword"
                  class="input_text form-control"
                />
                <i class="bi bi-search search_glass_announce"></i>
              </div>
            </form>
            <br />
          </div>
  
          <div class="notice_content">
            <div class="announcement-list">
              <div v-for="(data, index) in announcementList" :key="index">
                <router-link :to="'/announcement/' + data.ano" class="custom-link">
                  <h2 class="ano_title">&nbsp;&nbsp;{{ data.title }}</h2>
                  <p class="ano_date">{{ data.createDate }}</p>
                </router-link>
                <hr class="notice_line" />
              </div>
            </div>
            <p v-if="announcementList.length === 0">
              등록된 공지사항이 없습니다.
            </p>
            <router-link :to="'/faq'">
              <button type="button" class="btn btn-warning button23">
                <i class="bi bi-arrow-return-left"></i>
              </button>
            </router-link>
          </div>
          <br />
                 
          <div style="display: flex; justify-content: flex-end;">
  <router-link
    to="/adminanoadd"
    class="btn btn-warning btn-sm ml-3"
  >
    추가
  </router-link>
</div>


       
          <!-- 페이징 -->
          <div class="notice_paging">
            <ul class="paging pagination">
              <!-- 이전 버튼 -->
              <li
                class="page-arrow page-item"
                :class="{ disabled: pageIndex === 1 }"
              >
                <a
                  class="page-link"
                  href="#"
                  @click.prevent="goToPage(pageIndex - 1)"
                >
                  &laquo;
                </a>
              </li>
  
              <!-- 페이지 번호 -->
              <li
                v-for="page in totalPages"
                :key="page"
                class="page-item"
                :class="{ active: page === pageIndex }"
              >
  
                <a class="page-link" href="#" @click.prevent="goToPage(page)">
                  {{ page }}
                </a>
              </li>
  
              <!-- 다음 버튼 -->
  
              <li
                class="page-arrow page-item"
                :class="{ disabled: pageIndex === totalPages }"
              >
                <a
                  class="page-link"
                  href="#"
                  @click.prevent="goToPage(pageIndex + 1)"
                >
  
                  &raquo;
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    </div>
  </template>
  
  
  <script>
  import AnnouncementService from "@/services/faq/AnnouncementService";
  
  export default {
    data() {
      return {
        pageIndex: 1, // 현재 페이지
        totalPages: 5, // 전체 페이지 수
        searchKeyword: "", // 검색어
        announcementList: [], // 공지사항 데이터 리스트
      };
    },
    methods: {
      async getAnnouncements() {
        try {
          const response = await AnnouncementService.getAll(
            this.searchKeyword,
            this.pageIndex - 1,
            10 // 한 페이지에 표시할 데이터 개수
          );
          const { results, totalCount } = response.data;
          this.announcementList = results || [];
          this.totalPages = Math.ceil(totalCount / 10);
        } catch (error) {
          console.error("공지사항 데이터를 가져오는 중 에러 발생:", error);
        }
      },
      goToPage(page) {
        if (page > 0 && page <= this.totalPages) {
          this.pageIndex = page;
          this.updateQuery();
          this.getAnnouncements();
        }
      },
      searchAnnouncement() {
        this.pageIndex = 1;
        this.updateQuery();
        this.getAnnouncements();
      },
      updateQuery() {
        this.$router.push({
          path: this.$route.path,
          query: { search: this.searchKeyword },
        });
      },
    },
  
    mounted() {
      // 초기화 시 URL 쿼리값을 동기화
      this.searchKeyword = this.$route.query.search || "";
      this.getAnnouncements();
    },
  };
  </script>
  
  <style>
  .ano_top_title {
    font-weight: bolder;
    font-size: x-large;
    position: absolute;
    margin: 3px 0 0 30px;
  }
  
  /* 공지 전체 */
  .bigbox {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .lla,
  .new,
  .announce {
    color: #ffeb33;
    -webkit-text-stroke: 0.4px black;
    font-size: 16px;
    font-family: dohyeon;
  }
  
  /* 타이틀 */
  .title {
    text-align: center;
    font-weight: bolder;
    font-size: 25px;
  }
  
  /* 전체 박스 */
  .announce_body_box {
    width: 70%;
    border: 2.5px solid black;
    border-radius: 10px;
    padding: 15px;
  }
  
  /* 검색창 */
  .search_bar_announce {
    width: 35%;
    margin: 1px auto;
    border-radius: 25px;
    border: none;
    position: relative;
    background-color: white;
    margin-right: 10px;
    margin-bottom: 10px;
  }
  
  /* 돋보기 아이콘 */
  .search_glass_announce {
    position: absolute;
    right: 15px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 1.2rem;
    color: #ffeb33;
    cursor: pointer;
  }
  
  /* 입력 필드 */
  .input_text {
    margin: 10px auto;
    border-radius: 25px;
    border: 1.5px solid #ccc;
    padding: 5px 15px;
    position: relative;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: white;
  }
  
  /* 공지사항 리스트 스타일 */
  .notice_table {
    list-style: none;
    display: flex;
    flex-direction: column;
    padding: 0;
    margin: 7px 0 0 10px;
  }
  
  .notice_line {
    margin: 3px;
  }
  
  .ano_title {
    font-size: 23px;
    padding: 5px;
  }
  
  .ano_title:hover {
    transform: scale(1.01);
    transition: 0.2s;
  }
  
  .custom-link {
    text-decoration: none;
    color: inherit;
    /* 부모의 색상을 따릅니다 */
  }
  
  .custom-link:visited,
  .custom-link:active {
    text-decoration: none; /* 모든 상태에서 밑줄 제거 */
    color: inherit;
  }
  
  .custom-link:hover {
    transition: 0.3s;
  }
  
  /* 페이징 스타일 */
  .notice_paging .pagination {
    display: flex;
    justify-content: center;
    margin-top: 20px;
    padding: 10px;
  }
  
  .page-item {
    margin: 0 8px;
  }
  
  .page-link {
    color: #333;
    border: 1px solid #ccc;
    padding: 8px 16px;
    border-radius: 20px;
    font-size: 0.9rem;
    font-weight: bold;
    transition: all 0.3s ease;
  }
  
  .page-link:hover {
    background-color: #f5f5f5;
    color: #333;
    transform: scale(1.1);
  }
  
  .page-item.active .page-link {
    background-color: #ffeb33;
    color: #000;
    border: 1px solid #ffeb33;
    font-size: 1rem;
    font-weight: bold;
  }
  
  .page-item.disabled .page-link {
    color: #ccc;
    cursor: not-allowed;
  }
  
  .button23 {
    position: relative;
    margin-top: 10px;
    left: 94.5%;
  }
  
  .ano_date {
    position: absolute;
    margin: -37.5px 0 0 780px;
    font-size: 13px;
  }

  /* 관리자 강조 영역 */
.admin-dashboard {
  padding-top: 30px;
  padding-bottom: 30px;
}

/* 박스 컨테이너 */
.box-container {
  margin-bottom: 20px;
}

.box {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  text-align: center;
  transition: transform 0.3s ease;
}

.box:hover {
  transform: scale(1.05);
}

/* 아이콘 스타일 */
.box-icon {
  font-size: 3rem;
  color: #ffeb33; /* 노란색 강조 */
}

/* 타이틀 스타일 */
.box-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #343a40;
  margin-top: 10px;
}

/* 설명 텍스트 스타일 */
.box-description {
  font-size: 1rem;
  color: #6c757d;
}

/* 링크 스타일 */
.custom-link {
  text-decoration: none;
  color: inherit;
}

.custom-link:visited,
.custom-link:active {
  text-decoration: none;
  color: inherit;
}

.custom-link:hover {
  transition: 0.3s;
}











  </style>
  