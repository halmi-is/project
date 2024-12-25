<template>


<div class="container">

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
      <div class="title"></div>
      <hr />
      <div class="announce_body_box">
        <div class="notice_container">
          <div class="notice_search">
            <p class="faq_top_title" onclick="window.location.href='/faq/list'">
              자주 묻는 질문
            </p>
            <form class="search_input" @submit.prevent="searchFaq">
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
            <div class="accordion" id="faqAccordion">
              <div
                class="accordion-item"
                v-for="(data, index) in faqList"
                :key="index"
              >
                <h2 class="accordion-header" :id="'heading-' + index">
                  <button
                    class="accordion-button collapsed"
                    type="button"
                    data-bs-toggle="collapse"
                    :data-bs-target="'#collapse-' + index"
                    :aria-expanded="false"
                    :aria-controls="'collapse-' + index"
                  >
                    {{ data.question }}
                  </button>
                </h2>
                <div
                  :id="'collapse-' + index"
                  class="accordion-collapse collapse"
                  :aria-labelledby="'heading-' + index"
                  data-bs-parent="#faqAccordion"
                >
                  <div class="accordion-body">
                    {{ data.answer }}
                    <br />
                    <button
                      type="button"
                      class="btn btn-link"
                      @click="redirectToHashtag(data.hashtag)"
                    >
                      {{ data.hashtag }}
                    </button>
                    <router-link :to="`/faq/list/${data.fno}`" v-if="userRole === 'ROLE_ADMIN'">
                      <button type="button" class="btn btn-warning me-2">
                        수정
                      </button>
                    </router-link>
                  </div>
                </div>
              </div>
            </div>
            <p v-if="faqList.length === 0">등록된 질문이 없습니다.</p>
            <router-link :to="'/faq'" >
              <button type="button" class="btn btn-warning button">
                <i class="bi bi-arrow-return-left"></i>
              </button>
            </router-link>
          </div>
          <br />

          <div style="display: flex; justify-content: flex-end;">
  <router-link
    to="/adminquestionadd"
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
  import FaqService from "@/services/faq/FaqService";
  
  export default {
    data() {
      return {
        userRole: "",
        pageIndex: 1,
        totalPages: 1,
        searchKeyword: "",
        faqList: [],
      };
    },
    methods: {
      async getFaq() {
        try {
    const response = await FaqService.getAll(
      this.searchKeyword,
      this.pageIndex - 1,
      10
    );
    const { results, totalCount } = response.data;
    this.faqList = results || [];
    this.totalPages = totalCount > 0 ? Math.ceil(totalCount / 10) : 1;  // totalCount가 0일 경우 1페이지로 설정
  } catch (error) {
    console.error("FAQ 데이터를 가져오는 중 에러 발생:", error);
  }
      },
      goToPage(page) {
        if (page > 0 && page <= this.totalPages) {
          this.pageIndex = page;
          this.updateQuery();
          this.getFaq();
        }
      },
      searchFaq() {
        this.pageIndex = 1;
        this.updateQuery();
        this.getFaq();
      },
      searchByHashtag(hashtag) {
        this.searchKeyword = hashtag;
        this.searchFaq();
      },
      updateQuery() {
        this.$router.push({
            path: this.$route.path,
          query: { search: this.searchKeyword },
        });
      },
  
      redirectToHashtag(hashtag) {
        const sanitizedHashtag = hashtag.replace(/^#/, ""); // `#` 제거
        this.searchKeyword = sanitizedHashtag;
        this.pageIndex = 1; // 검색 시 첫 페이지로 초기화
        this.updateQuery(); // URL 쿼리 업데이트
        this.getFaq(); // 데이터 갱신
      },
    },
    // watch: {
    //   "$route.query.search"(newQuery) {
    //     if (newQuery !== this.searchKeyword) {
    //       this.searchKeyword = newQuery || "";
    //       this.getFaq();
    //     }
    //   },
    // },
    mounted() {
      const user = localStorage.getItem("user");
      if (user) {
        const parsedUser = JSON.parse(user);
        this.userRole = parsedUser.codeName;
      } else {
        console.error("No user data found in localStorage.");
      }
  
      // 초기화 시 URL 쿼리값을 동기화
      this.searchKeyword = this.$route.query.search || "";
      this.getFaq();
    },
  };
  </script>
  
  <style>
  .custom-link {
    text-decoration: none;
    color: inherit; /* 부모의 색상을 따릅니다 */
  }
  
  .custom-link:visited,
  .custom-link:active {
    text-decoration: none; /* 모든 상태에서 밑줄 제거 */
    color: inherit;
  }
  .custom-link:hover {
    color: #ffeb33;
    transition: 0.3s;
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
  
  .faq_top_title {
    font-weight: bolder;
    font-size: x-large;
    position: absolute;
    margin: 3px 0 0 30px;
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
  
  .notice_title {
    text-decoration: none;
    color: #333;
    font-weight: bold;
    display: flex;
    justify-content: space-between;
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
  .button {
    position: relative;
    margin-top: 10px;
    left: 94.5%;
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
  