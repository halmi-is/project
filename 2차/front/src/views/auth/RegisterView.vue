<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-xl-6 col-lg-8 col-md-10">
        <div class="card custom-card mt-5">
          <div class="card-body p-0">
            <div class="row">
              <!-- TODO: email/password 입력양식  -->
              <div class="col-lg-12">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">회원가입</h1>
                  </div>
                  <div class="user">
                    <div class="form-group">
                      <input
                        type="email"
                        class="form-control form-control-user mb-3"
                        placeholder="이메일을 입력하세요."
                        v-model="user.email"
                      />
                    </div>
                    <div class="form-group">
                      <input
                        type="password"
                        class="form-control form-control-user mb-3"
                        placeholder="패스워드를 입력하세요."
                        v-model="user.password"
                      />
                      <div class="form-group">
                        <input
                          type="text"
                          class="form-control form-control-user mb-3"
                          placeholder="이름을 입력하세요."
                          v-model="user.name"
                        />
                      </div>
                      <div class="form-group">
                        <input
                          type="password"
                          class="form-control form-control-user mb-3"
                          placeholder="주민등록번호를 입력하세요."
                          v-model="user.ssn"
                        />
                      </div>
                    </div>
                    <button
                      button
                      class="btn btn-custom w-100 mb-3"
                      @click="register"
                    >
                      Sign Up
                    </button>
                    <!-- 에러 메시지 표시 -->
                    <div
                      v-if="errorMessage"
                      class="alert alert-danger text-center"
                      role="alert"
                      v-html="errorMessage"
                    ></div>
                  </div>
                  <hr />
                  <div class="text-center">
                    <a href="/login">
                      이미 계정이 있으신가요? 로그인 하세요!
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MemberService from "@/services/auth/MemberService";
import Swal from "sweetalert2";
export default {
  data() {
    return {
      user: {
        email: "",
        password: "",
        name: "",
        ssn: "",
        codeName: "ROLE_USER",
      },

      errorMessage: "", // 에러 메시지 상태 관리
    };
  },
  methods: {
    validateForm() {
      // 이메일 형식 검증: @ 포함 여부와 . 포함 여부, 그리고 최소 한 글자 이상
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      if (!this.user.email.trim()) {
        this.errorMessage = "이메일을 입력해주세요.";
        return false;
      }

      if (!emailPattern.test(this.user.email)) {
        this.errorMessage = "올바른 이메일 형식을 입력해주세요.";
        return false;
      }

      // 비밀번호 빈 값 체크
      if (!this.user.password.trim()) {
        this.errorMessage = "비밀번호를 입력해주세요.";
        return false;
      }

      // 이름 유효성 검사: 숫자나 특수기호 포함 여부
      const namePattern = /^[a-zA-Z가-힣\s]+$/; // 한글, 영문자, 공백만 허용

      if (!this.user.name.trim()) {
        this.errorMessage = "이름을 입력해주세요.";
        return false;
      }

      if (!namePattern.test(this.user.name)) {
        this.errorMessage = "이름에는 숫자나 특수기호를 사용할 수 없습니다.";
        return false;
      }

      // 주민등록번호 숫자 체크
      const ssnPattern = /^\d{13}$/; // 정확히 13자리 숫자만 허용.

      if (!this.user.ssn.trim()) {
        this.errorMessage = "주민등록번호를 입력해주세요.";
        return false;
      }

      if (!ssnPattern.test(this.user.ssn)) {
        this.errorMessage = "주민등록번호는 13자리 숫자로 입력해주세요.";
        return false;
      }

      // 모든 검증 통과 시 에러 메시지 초기화
      this.errorMessage = "";
      return true;
    },

    async register() {
      if (!this.validateForm()) {
        return; // 유효성 검사 실패 시 요청 중단
      }

      try {
        let response = await MemberService.insert(this.user);
        console.log(response.data);

        // SweetAlert2를 사용한 회원가입 성공 메시지
        Swal.fire({
          icon: "success",
          title: "회원가입 성공",
          text: "회원가입이 완료되었습니다!",
          confirmButtonText: "확인",
          timer: 2000, // 2초 후 자동 닫힘
          timerProgressBar: true,
        });

        this.$router.push("/login");
      } catch (error) {
        // SweetAlert2를 사용한 에러 메시지
        Swal.fire({
          icon: "error",
          title: "회원가입 실패",
          text: "이미 가입된 이메일이 있습니다.",
          confirmButtonText: "확인",
        });

        console.log(error);
      }
    },
  },
  mounted() {
    if (this.$store.state.loggedIn == true) {
      this.$router.push("/");
    }
  },
};
</script>

<style>
/* 에러 메시지 디자인 시작 */
.alert {
  font-size: 0.9rem;
  border-radius: 5px;
  padding: 10px 15px;
}
.alert-danger {
  background-color: #f8d7da;
  color: #842029;
  border: 1px solid #f5c2c7;
}
.alert .bi {
  font-size: 1.2rem;
}
/* 에러 메시지 디자인 끝 */

/* 회원가입 버튼 디자인 시작 */
.btn-custom {
  background-color: black;
  color: white;
  border-radius: 50px;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
}

.btn-custom:hover,
.btn-custom:focus,
.btn-custom:active {
  background-color: black;
  color: white;
  outline: none;
  box-shadow: none;
}
/* 회원가입 버튼 디자인 끝 */

/* 카드의 테두리 색상을 흰색으로 설정 */
.custom-card {
  background-color: #f8f9fa; /* 배경색: 라이트그레이 */
  border: 1px solid white; /* 테두리 흰색 */
  border-radius: 30px; /* 둥근 모서리 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 은은한 그림자 */
}
</style>
