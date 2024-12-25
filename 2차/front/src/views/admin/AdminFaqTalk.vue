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
                            <div class="notice_tab_area nav">
                                <div class="sub_tab_list" role="tablist"></div>
                            </div>
                            <br />
                        </div>
                        <div class="notice_content">
                            <p class="talk_big_title">문의 등록</p>
                            <br />
                            <form @submit.prevent="validateAndSave">
                                <div class="title_category_box">
                                    <!-- 제목 -->
                                    <div class="form-floating talk_title">
                                        <input type="text" class="form-control" id="validationDefault01"
                                            placeholder="제목" required v-model="talk.title" />
                                        <label for="validationDefault01">제목</label>
                                    </div>
                                    <br />
                                    <!-- 카테고리 -->
                                    <div class="form-floating talk_category">
                                        <select class="form-select" id="validationDefault04" placeholder="카테고리" required
                                            v-model="talk.category">
                                            <option selected disabled value="">
                                                카테고리를 선택하세요
                                            </option>
                                            <option>예약</option>
                                            <option>보험</option>
                                            <option>결제</option>
                                            <option>계정</option>
                                            <option>기타</option>
                                        </select>
                                        <label for="validationDefault04">State</label>
                                    </div>
                                </div>
                                <br />
                                <!-- 내용 -->
                                <div class="form-floating talk_content">
                                    <textarea type="text" class="form-control" id="validationDefault02" placeholder="내용"
                                        required style="height: 250px" v-model="talk.content"></textarea>
                                    <label for="validationDefault02">내용</label>
                                </div>
                                <br />
                                <!-- 체크 -->
                                <div class="form-check talk_round">
                                    <input class="form-check-input" type="checkbox" value="" id="invalidCheck2"
                                        v-model="isChecked" required />
                                    <label class="form-check-label" for="invalidCheck2">
                                        제출 전에 작성한 내용이 맞는지 확인
                                    </label>
                                </div>
                                <br />
                                <div class="talk_button">
                                    <button class="btn btn-primary" disabled>
                                        제출
                                    </button>
                                    <button type="button" class="btn btn-success back_button" disabled>
                                        문의목록
                                    </button>

                                    <button type="button" class="btn btn-warning back_button" disabled>
                                        <i class="bi bi-arrow-return-left"></i>
                                    </button>
                                </div>
                            </form>
                        </div>
                        <br />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import TalkService from "@/services/faq/TalkService";

export default {
    data() {
        return {
            isChecked: false, // 최상위 데이터에 선언
            talk: {
                title: "",
                content: "",
                category: "",
            },
        };
    },
    methods: {
        async save() {
            try {
                let response = await TalkService.insert(this.talk);
                console.log("저장 성공:", response.data);
                alert("저장되었습니다.");
                this.$router.push("/faq/talklist");
            } catch (error) {
                console.error("저장 실패:", error);
            }
        },
        validateAndSave() {
            if (!this.isChecked) {
                alert("작성한 내용이 맞는지 체크해주세요.");
                return;
            }
            this.save();
        },
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

/* 공지 전체 */
.bigbox {
    width: 80%;
    /* 가로 길이 조정 */
    margin: -25px auto;
    /* 페이지 가운데 배치 */
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
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
    width: 85%;
    border: 2.5px solid black;
    border-radius: 10px;
    padding: 15px;
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

.title_category_box {
    display: flex;
}

/* 제목 */
.talk_title {
    margin-left: 14%;
    margin-right: 2%;
    width: 40%;
}

/* 카테고리 */
.talk_category {
    width: 30%;
}

/* 내용, 사진 */
.talk_content,
.talk_picture {
    margin-left: 14%;
    width: 72%;
}

/* 라운드, 버튼 */
.talk_round,
.talk_button {
    margin-left: 14%;
}

.talk_button {
    display: flex;
    justify-content: space-between;
    width: 72%;
}

.talk_big_title {
    font-size: 24px;
    font-weight: bold;
    text-align: center;
}
</style>
