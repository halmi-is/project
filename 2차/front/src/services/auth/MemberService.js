import axios from "axios";

const baseURL = "http://localhost:8000/api"

// TODO: 회원가입
const insert = (data) => {
    return axios.post(baseURL + `/auth/register`, data);
};

// TODO: 로그인
const login = (data) => {
    console.log(data);
    return axios.post(baseURL + `/auth/login`, data);
};

// TODO: 로그아웃 : 
const logout = () => {
    localStorage.removeItem("user"); 
};

// TODO: 이메일 찾기
const findEmail = (data) => {
    return axios.post(baseURL + `/auth/find-email`, data);
};

// TODO: 비밀번호 변경
const updatePassword = (data) => {
    return axios.put(baseURL + `/auth/update-password`, data);
};

const MemberService = {
    insert,
    login,
    logout,
    findEmail,
    updatePassword,
}


export default MemberService;