<template>
    <div class="container">
        <div class="main-layout">
            <!-- 버튼 그룹 박스 (왼쪽) -->
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
                <div class="announce_body_box">
                    <div class="notice_container">
                        <div class="notice_search">
                            <div class="notice_tab_area nav">
                                <div class="sub_tab_list" role="tablist"></div>
                            </div>
                            <br />
                        </div>
                        <div class="notice_content">
                            <h1 class="title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;결제 방법 안내</h1>
                            <br />
                            <div class="payment_section">
                                <h2 class="second_title">&nbsp; 1. 신용/체크카드 결제</h2>
                                <hr />
                                <ol>
                                    <li>
                                        <strong>상품 선택:</strong> 결제하고자 하는 상품을 장바구니에
                                        담은 후, 결제 버튼을 클릭합니다.
                                    </li>
                                    <li>
                                        <strong>결제 수단 선택:</strong> '신용/체크카드'를 선택한 뒤,
                                        카드 정보를 입력합니다.
                                    </li>
                                    <li>
                                        <strong>카드 인증:</strong> 결제 시 카드사의 인증 절차(예: OTP,
                                        앱 인증 등)를 완료해야 합니다.
                                    </li>
                                    <li>
                                        <strong>결제 완료 확인:</strong> 결제가 완료되면 확인 페이지로
                                        이동하며, 이메일로 영수증이 발송됩니다.
                                    </li>
                                </ol>
                            </div>
                            <br />
                            <div class="payment_section">
                                <h2 class="second_title">
                                    &nbsp;2. 간편결제(네이버페이/카카오페이 등)
                                </h2>
                                <hr />
                                <ol>
                                    <li>
                                        <strong>상품 선택:</strong> 구매할 상품을 선택한 후 결제
                                        페이지로 이동합니다.
                                    </li>
                                    <li>
                                        <strong>간편결제 선택:</strong> 사용하고자 하는 간편결제
                                        수단(예: 네이버페이, 카카오페이)을 선택합니다.
                                    </li>
                                    <li>
                                        <strong>로그인 및 인증:</strong> 간편결제 계정으로 로그인하고,
                                        인증을 진행합니다.
                                    </li>
                                    <li>
                                        <strong>결제 완료:</strong> 인증이 끝나면 결제가 자동으로
                                        처리되며, 결제 완료 메시지가 표시됩니다.
                                    </li>
                                </ol>
                            </div>
                            <br />
                            <div class="payment_section">
                                <h2 class="second_title">&nbsp;3. 해외 결제</h2>
                                <hr />
                                <ol>
                                    <li>
                                        <strong>상품 선택:</strong> 구매하려는 상품을 선택한 뒤 결제
                                        페이지로 이동합니다.
                                    </li>
                                    <li>
                                        <strong>해외 결제 활성화:</strong> 카드사가 해외 결제를
                                        허용했는지 확인하고, 필요 시 고객센터를 통해 활성화합니다.
                                    </li>
                                    <li>
                                        <strong>외화 환율 확인:</strong> 결제 금액이 외화로 표시되므로,
                                        환율을 확인합니다.
                                    </li>
                                    <li>
                                        <strong>결제 정보 입력:</strong> 카드 번호, 유효기간, CVC 코드
                                        등 해외 결제용 정보를 입력합니다.
                                    </li>
                                    <li>
                                        <strong>결제 완료:</strong> 국제 인증 과정을 거쳐 결제가
                                        완료되면 확인 메시지가 표시됩니다.
                                    </li>
                                </ol>
                            </div>
                            <br />
                            <div class="payment_section">
                                <h2 class="second_title">&nbsp;4. 무통장 입금</h2>
                                <hr />
                                <ol>
                                    <li>
                                        <strong>계좌 정보 확인:</strong> 결제 페이지에서 제공된 계좌
                                        번호를 확인합니다.
                                    </li>
                                    <li>
                                        <strong>입금 진행:</strong> 온라인 뱅킹 또는 ATM을 이용해 해당
                                        계좌로 입금합니다.
                                    </li>
                                    <li>
                                        <strong>입금 확인:</strong> 입금 후 결제 페이지에서 '입금 확인'
                                        버튼을 눌러 완료를 알립니다.
                                    </li>
                                    <li>
                                        <strong>결제 완료:</strong> 관리자가 입금을 확인하면 결제 완료
                                        메시지가 발송됩니다.
                                    </li>
                                </ol>
                            </div>
                            <br />
                            <div class="payment_section">
                                <h2 class="second_title">&nbsp;5. 결제 후 주의사항</h2>
                                <hr>
                                <ul>
                                    <li>결제 완료 후 이메일로 발송된 영수증을 꼭 확인하세요.</li>
                                    <li>문제가 발생할 경우, 고객센터로 연락하여 지원을 받으세요.</li>
                                    <li>환불 정책은 상품마다 다를 수 있으니 확인 바랍니다.</li>
                                </ul>
                            </div>
                        </div>
                        <br />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import AdminService from "@/services/admin/AdminService";

export default {
    data() {
        return {
            pageIndex: 1, // 현재 페이지 번호
            totalCount: 0, // 전체 개수
            recodeCountPerPage: 3, // 화면에 보일 개수
            searchKeyword: "", // 검색어
            admins: [], // 빈 배열 (json)
        };
    },
    methods: {
        async getAdmin() {
            try {
                const response = await AdminService.getAll(
                    this.searchKeyword,
                    this.pageIndex - 1,
                    this.recodeCountPerPage
                );
                const { results, totalCount } = response.data;
                this.admins = results; // 받아온 데이터 저장
                this.totalCount = totalCount; // 총 개수 저장
            } catch (error) {
                console.error("데이터를 가져오는 중 오류 발생:", error);
            }
        },
    },
    mounted() {
        this.getAdmin();
    },
};
</script>

<style>
.container {
    width: 100%;
    position: relative;
    top: 0;
    /* 상단 고정 */
    left: 0;
    /* 좌측 고정 */
    overflow: visible;
    padding: 20px;
    padding-bottom: 20px
        /* 안쪽 여백 설정 */

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
    gap: 20px;
}


/* 공지 전체 */
.bigbox {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    /* 내용이 위쪽에 정렬되도록 설정 */
    align-items: center;
    min-height: 100%;
    /* 최소 높이를 100%로 설정 */
    overflow: auto;
    /* 내용이 넘칠 경우 스크롤이 생기도록 설정 */
}

/* 타이틀 */
.title {
    font-size: 22px;
    font-weight: bold;
    color: #333;
}

.second_title {
    margin: 5px 0;
    font-size: 20px;
}

/* 전체 박스 */
.announce_body_box {
    width: 92%;
    border: 2.5px solid black;
    border-radius: 10px;
    padding: 15px;
    background-color: #f9f9f9;
    margin-left: 75px;
}

/* 내용 섹션 */
.payment_section {
    padding: 10px 15px;
    border: 1px solid #ddd;
    border-radius: 5px;
    margin-bottom: 15px;
    background-color: #fff;
}

/* 목록 스타일 */
.payment_section ol,
.payment_section ul {
    margin-left: 20px;
}

.payment_section li {
    margin-bottom: 10px;
}

/* 강조 텍스트 */
.payment_section strong {
    font-weight: bold;
    color: #333;
}
</style>