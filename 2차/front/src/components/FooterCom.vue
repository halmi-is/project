<template>
  <div class="full-foot">
    <nav class="bg-light">
      <hr />
      <br />
      <div class="footer-container">
        <div class="downfoot-container">
          <p class="downfoot">
            상호: (주)지조결L.L.A &nbsp;&nbsp;&nbsp;&nbsp;대표 : 코드블루
            &nbsp;&nbsp;&nbsp;&nbsp;주소 : 부산시 동래구
          </p>
          <p class="downfoot">
            대표번호 : 02-555-5000 &nbsp;&nbsp;&nbsp;&nbsp;이메일 :
            LLAJJG@example.com
          </p>
          <p class="downfoot">사업자등록번호 : 123-45-67890</p>
          <p class="downfoot">
            관광사업자 등록번호 : 제2423-42883호
            &nbsp;&nbsp;&nbsp;&nbsp;개인정보보호 책임자 : 갓창혁
          </p>
          <p class="downfoot">Copyright © 2023 L.L.A</p>
          <p class="downfoot">ALL RIGHTS RESERVED</p>
          <p class="downfoot">This page was created with L.L.A</p>
        </div>
        <div class="upfoot-container">
          <p class="upfoot">고객센터 : 02-555-5000</p>
          <p class="upfoot">주소 : 부산시 동래구</p>
          <p class="upfoot">상담시간 : 평일 09:00 ~ 09:10</p>
          <p class="upfoot">사업자번호 : 123-45-67890</p>
          <p class="upfoot">Copyright © 2023 L.L.A</p>
        </div>
      </div>
    </nav>
  </div>
</template>
<script>
export default {};
</script>
<style>
.full-foot {
  margin-top: 50px;
}
.footer-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 10px 15%;
  background-color: #f8f9fa;
}

.downfoot-container,
.upfoot-container {
  width: 45%;
}

.upfoot {
  font-size: 13px;
  text-align: right;
  margin-right: 18.5%;
  position: relative;
  top: 10%;
}
.downfoot {
  font-size: 12.5px;
  margin-left: 18%;
  position: relative;
}
.footer_menu {
  background-color: #f8f9fa;
}
/* 최상단 메뉴 링크 */
.footer_menu_link {
  display: inline-flex;
  font-size: 12px;
  margin-left: 17%;
  margin-top: -0.5%;
  text-decoration: none;
  color: inherit;
}
.footer_menu_link:visited,
.ooter_menu_link:active {
  text-decoration: none; /* 모든 상태에서 밑줄 제거 */
  color: inherit;
}
.footer_menu_link .nav-link {
  color: black !important; /* 원하는 색상 (주황색) */
  text-decoration: none;
  color: inherit;
}
/* 링크 색상 변경 - hover 상태 */
.footer_menu_link .nav-link:hover {
  color: #d1cc9b !important; /* hover 시 색상 (빨간색) */
}
.footer_menu_link .nav-item {
  margin-right: -25px; /* 아이템 간 오른쪽 간격 */
}
</style>
