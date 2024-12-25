<template>
    <div class="faq-container">
        <div class="card p-5 mt-5">
            <h3 class="text-center mb-4 ">FAQ 수정</h3>

            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="question" placeholder="질문" v-model="faq.question" />
                <label for="question">질문</label>
            </div>

            <div class="form-floating mb-3 large-input">
                <input type="text" class="form-control large-input" id="answer" placeholder="답변" v-model="faq.answer" />
                <label for="answer">답변</label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="hashtag" placeholder="해시태그" v-model="faq.hashtag" />
                <label for="hashtag">해시태그</label>
            </div>

            <div class="d-flex justify-content-between mt-4">
                <button @click="getFaqUpdate" class="btn btn-warning btn-lg flex-grow-1 me-2">
                    수정
                </button>
                <button @click="getFaqDelete" class="btn btn-danger btn-lg flex-grow-1">
                    삭제
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import FaqService from "@/services/faq/FaqService";
export default {
    data() {
        return {
            faq: {
                question: "",
                answer: "",
                hashtag: "",
            },
        };
    },
    methods: {
        async getFaqDetails(fno) {
            try {
                const response = await FaqService.getDetail(fno);
                if (response && response.data) {
                    this.faq = response.data;
                }
            } catch (error) {
                console.error("Error fetching FAQ details:", error);
            }
        },

        async getFaqUpdate() {
            try {
                const response = await FaqService.getUpdate(this.faq.fno, this.faq);
                console.log(response.data);
                this.$router.go(-1);
            } catch (error) {
                console.error("Error updating FAQ:", error);
            }
        },

        async getFaqDelete() {
            try {
                const response = await FaqService.getDelete(this.faq.fno);
                console.log(response.data);
                this.$router.go(-1);
            } catch (error) {
                console.error("Error deleting FAQ:", error);
            }
        },
    },
    mounted() {
        const fno = this.$route.params.fno;
        if (fno) {
            this.getFaqDetails(fno);
        } else {
            console.error("FAQ ID is missing in the route.");
        }
    },
};
</script>

<style>
.container {
    width: 100%;
    height: 1000px;
    position: relative;
    top: 0;
    left: 0;
    overflow-y: auto;
    padding: 20px;
}

/* 버튼 그룹 */
#main_button_group {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 20px;
    gap: 15px;
    width: 200px;
    /* 버튼 그룹 박스 크기 증가 */
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 10px;

}

.custom-button {
    font-size: 16px;
    font-weight: bold;
    color: #333;
    border: 2px solid #ccc;
    text-align: center;
    background: #fff;
    transition: all 0.3s;
}

.custom-button:hover {
    background-color: #464444;
    color: white;
    border-color: #464444;
    transform: scale(1.05);
}

/* 메인 레이아웃 */
.main-layout {
    display: flex;
    gap: 30px;
    justify-content: center;
}

/* 큰 박스 */
.bigbox {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    width: 75%;
    max-width: 1200px;
    background: white;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 12px;
}

.large-input {
    width: 100%;

}

/* 검색창 */
.search_bar_announce {
    width: 50%;
    margin: 0 auto 20px auto;
    border-radius: 25px;
    position: relative;
    background-color: #fff;
    transition: all 0.3s;
}



.search_glass_announce {
    position: absolute;
    right: 15px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 1.5rem;
    color: #ffeb33;
    cursor: pointer;
}

/* 입력 필드 */
.input_text {
    width: 100%;
    padding: 12px 15px;
    border: 1.5px solid #ccc;
    border-radius: 25px;
    font-size: 1rem;
}

/* 아코디언 */
.accordion-item {
    margin-bottom: 10px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background: #fff;
}

.accordion-header {
    font-size: 1.2rem;
    font-weight: bold;
    color: #333;
}

.accordion-button {
    background: #f9f9f9;
    border: none;
    font-size: 1rem;
    padding: 10px 15px;
    border-radius: 8px;
    transition: all 0.3s;
}

.accordion-button:hover {
    background: #ffeb33;
    color: #000;
}

.accordion-body {
    font-size: 1rem;
    line-height: 1.6;
    padding: 15px;
}

/* 페이징 스타일 */
.notice_paging .pagination {
    display: flex;
    justify-content: center;
    margin-top: 20px;
    gap: 8px;
}

.page-link {
    color: #333;
    border: 1px solid #ccc;
    padding: 8px 16px;
    border-radius: 20px;
    font-size: 0.9rem;
    font-weight: bold;
    transition: all 0.3s;
}

.page-link:hover {
    background-color: #464444;
    color: white;
}

.page-item.active .page-link {
    background-color: #ffeb33;
    color: #000;
    border-color: #ffeb33;
}
</style>
