<template>
    <div class="container">
        <div class="admin-layout">
            <!-- 관리자 페이지 타이틀 -->
            <div class="admin-header">
                <h1>관리자 대시보드</h1>
                <p>귀하의 웹사이트 관리 기능을 확인하세요</p>
            </div>

            <!-- 버튼 그룹 박스 -->
            <div id="main_button_group" class="custom-button-group">
                <b-button variant="outline-dark" class="custom-button" href="/">
                    <i class="bi bi-house-door custom-icon"></i><br />메인페이지
                </b-button>
                <b-button variant="outline-dark" class="custom-button" href="/add-main">
                    <i class="bi bi-journal-plus custom-icon"></i><br />메인 추가 페이지
                </b-button>
                <b-button variant="outline-dark" class="custom-button" href="/coupon">
                    <i class="bi bi-ticket custom-icon"></i><br />쿠폰 페이지
                </b-button>
                <b-button variant="outline-dark" class="custom-button" href="/recommend">
                    <i class="bi bi-star custom-icon"></i><br />추천여행지 페이지
                </b-button>
                <b-button variant="outline-dark" class="custom-button" href="/review">
                    <i class="bi bi-pencil custom-icon"></i><br />리뷰게시판
                </b-button>
                <b-button variant="outline-dark" class="custom-button" href="/adminfaqannouncement">
                    <i class="bi bi-question-circle custom-icon"></i><br />FAQ 페이지
                </b-button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'AdminPage'
};
</script>

<style>
/* 전체 컨테이너 스타일 */
.container {
    width: 100%;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: flex-start;
    padding-top: 60px;
}

/* 관리자 페이지 레이아웃 */
.admin-layout {
    width: 80%;
    max-width: 1200px;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 40px;
    display: flex;
    flex-direction: column;
    gap: 30px;
}

/* 관리자 페이지 헤더 */
.admin-header {
    text-align: center;
}

.admin-header h1 {
    font-size: 2.5rem;
    color: #333;
    margin-bottom: 10px;
    font-weight: bold;
}

.admin-header p {
    font-size: 1rem;
    color: #555;
    font-weight: 300;
}

/* 버튼 그룹 스타일 */
.custom-button-group {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    row-gap: 40px;
    /* 각 줄 간격을 40px로 설정 */
    column-gap: 20px;
    /* 열 간격은 기존 20px 유지 */
    width: 100%;
    justify-items: center;
}

/* 버튼 스타일 */
.custom-button {
    font-size: 16px;
    font-weight: bold;
    color: #333;
    border: 2px solid #ccc;
    text-align: center;
    width: 200px;
    height: 120px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border-radius: 10px;
    background-color: #f9f9f9;
    transition: all 0.3s ease;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

/* 버튼 호버 효과 */
.custom-button:hover {
    background-color: #ffeb33;
    border-color: #ffeb33;
    color: white;
    transform: translateY(-5px);
}

/* 아이콘 스타일 */
.custom-icon {
    font-size: 40px;
    color: #333;
    margin-bottom: 8px;
}

/* 버튼 포커스 효과 */
.custom-button:focus {
    outline: none;
    box-shadow: 0 0 0 2px rgba(255, 235, 51, 0.6);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
    .custom-button-group {
        grid-template-columns: repeat(2, 1fr);
        /* 작은 화면에서는 2개씩 배치 */
    }
}

@media (max-width: 480px) {
    .custom-button-group {
        grid-template-columns: 1fr;
        /* 더 작은 화면에서는 1개씩 배치 */
    }
}
</style>
