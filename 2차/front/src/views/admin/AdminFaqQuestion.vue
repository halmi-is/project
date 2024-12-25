<template>
    <div class="container">
        <div class="main-layout">
            <div id="main_button_group" class="custom-button-group">
                <b-button variant="outline-dark" class="custom-button" href="/mainadmin1">
                    <i class="bi bi-chat-square-dots custom-icon"></i><br />1:1 문의
                </b-button>
                <b-button variant="outline-dark" class="custom-button" href="/mainadmin2">
                    <i class="bi bi-receipt-cutoff custom-icon"></i><br />질문 게시판
                </b-button>
                <b-button variant="outline-dark" class="custom-button" href="/mainadmin3">
                    <i class="bi bi-cash-coin custom-icon"></i><br />결제 방법
                </b-button>
                <b-button variant="outline-dark" class="custom-button" href="/mainadmin5">
                    <i class="bi bi-megaphone custom-icon"></i><br />공지사항
                </b-button>
            </div>

            <div class="bigbox">
                <hr />
                <div class="announce_body_box">
                    <div class="notice_container">
                        <div class="notice_search">
                            <form class="search_input" @submit.prevent="searchFaq">
                                <div class="input_box typing form-group search_bar_announce">
                                    <input placeholder="제목, 내용" v-model="searchKeyword"
                                        class="input_text form-control" />
                                    <i class="bi bi-search search_glass_announce"></i>
                                </div>
                            </form>
                        </div>

                        <div class="notice_content">
                            <div class="accordion" id="faqAccordion">
                                <div class="accordion-item" v-for="(data, index) in faqList" :key="index">
                                    <h2 class="accordion-header" :id="'heading-' + index">
                                        <button class="accordion-button collapsed" type="button"
                                            data-bs-toggle="collapse" :data-bs-target="'#collapse-' + index"
                                            :aria-expanded="false" :aria-controls="'collapse-' + index">
                                            {{ data.question }}
                                            <div class="upde">
                                                <button class="updede" @click="upde(data.fno)">
                                                    수정/삭제
                                                </button>
                                            </div>
                                        </button>
                                    </h2>
                                    <div :id="'collapse-' + index" class="accordion-collapse collapse"
                                        :aria-labelledby="'heading-' + index" data-bs-parent="#faqAccordion">
                                        <div class="accordion-body">
                                            {{ data.answer }}
                                            <br />
                                            <button type="button" class="btn btn-link"
                                                @click="redirectToHashtag(data.hashtag)">
                                                {{ data.hashtag }}
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <p v-if="faqList.length === 0">등록된 질문이 없습니다.</p>
                        </div>

                        <div class="notice_paging">
                            <ul class="paging pagination">
                                <li class="page-arrow page-item" :class="{ disabled: pageIndex === 1 }">
                                    <a class="page-link" href="#" @click.prevent="goToPage(pageIndex - 1)">
                                        &laquo;
                                    </a>
                                </li>

                                <li v-for="page in totalPages" :key="page" class="page-item"
                                    :class="{ active: page === pageIndex }">
                                    <a class="page-link" href="#" @click.prevent="goToPage(page)">
                                        {{ page }}
                                    </a>
                                </li>

                                <li class="page-arrow page-item" :class="{ disabled: pageIndex === totalPages }">
                                    <a class="page-link" href="#" @click.prevent="goToPage(pageIndex + 1)">
                                        &raquo;
                                    </a>
                                </li>
                            </ul>
                        </div>
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
                this.totalPages = Math.ceil(totalCount / 10);
            } catch (error) {
                console.error("FAQ 데이터를 가져오는 중 에러 발생:", error);
            }
        },
        goToPage(page) {
            if (page > 0 && page <= this.totalPages) {
                this.pageIndex = page;
                this.getFaq();
            }
        },
        searchFaq() {
            this.pageIndex = 1;
            this.getFaq();
        },
        searchByHashtag(hashtag) {
            this.searchKeyword = hashtag;
            this.searchFaq();
        },
        updateQuery() {
            this.$router.push({
                path: "list",
                query: { search: this.searchKeyword },
            });
        },

        redirectToHashtag(hashtag) {
            const sanitizedHashtag = hashtag.replace(/^#/, "");
            this.searchKeyword = sanitizedHashtag;
            this.pageIndex = 1;
            this.getFaq();
        },

        upde(fno) {
            this.$router.push(`/admin/list/${fno}`);
        }
    },
    mounted() {
        this.searchKeyword = this.$route.query.search || "";
        this.getFaq();
    },
};
</script>

<style>
.container {
    width: 100%;
    min-height: 100vh;
    position: relative;
    padding: 20px;
    box-sizing: border-box;
}

.custom-icon {
    font-size: 40px;
    color: #ffeb33;
    margin-bottom: 0.5rem;
}

#main_button_group {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 10px;
    gap: 10px;
    width: 200px;
}

.custom-button-group .btn {
    margin: 0 20px;
    margin-bottom: 20px;
    width: 100%;
    height: 100px;
    text-align: center;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.custom-button {
    font-size: 15px;
    font-weight: bold;
    color: #333;
    border: 2px solid #ccc;
    text-align: center;
}

.custom-button:hover {
    background-color: #464444;
    border-color: #ccc;
    color: white;
}

.main-layout {
    display: flex;
}

.bigbox {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 80%;
    /* 너비를 90%로 설정, !important 추가 */
    /* 최대 너비를 1600px로 설정, !important 추가 */
    margin: -30px auto;
    padding: 5px 10px 10px;
}

.announce_body_box {
    width: 80%;
    border: 2.5px solid black;
    border-radius: 10px;
    padding: 15px;
    margin-left: -50px;
}

.search_bar_announce {
    width: 35%;
    margin: 1px auto;
    border-radius: 25px;
    border: none;
    position: relative;
    background-color: white;
    margin-right: 10px;
    margin-bottom: 40px;
}

.search_glass_announce {
    position: absolute;
    right: 15px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 1.2rem;
    color: #ffeb33;
    cursor: pointer;
}

.input_text {
    margin: 10px auto;
    border-radius: 25px;
    border: 1.5px solid #ccc;
    padding: 5px 15px;
    position: relative;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: white;
}

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

.accordion-button {
    background-color: #f1f1f1;
    color: #333;
    font-size: 1.1rem;

    padding: 10px 15px;
    transition: all 0.3s ease;
}

.accordion-button.collapsed {
    background-color: white;
    color: #333;
    padding: 20px 0 20px 10px;
}

.accordion-body {
    padding: 15px;
    font-size: 1rem;
    background-color: #f9f9f9;
    color: #333;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.upde {
    position: absolute;
    right: 30px;
    top: 50%;
    transform: translateY(-50%);
}

.updede {
    background-color: #ffc107;
    border-radius: 8px;
    border: 1px solid #ffc107;
    padding: 4px 10px;
    font-size: 1rem;
    font-weight: bold;
    color: white;
    text-transform: uppercase;
    cursor: pointer;
    transition: all 0.3s ease;
}

.updede:hover {
    background-color: #ff9800;
    border-color: #ff9800;
    color: white;
    transform: scale(1.05);
}

.updede:active {
    background-color: #e68900;
    border-color: #e68900;
    transform: scale(1);
}

.updede:focus {
    outline: none;
    box-shadow: 0 0 0 2px rgba(255, 193, 7, 0.5);
}
</style>
