import axios from "axios";
// 백엔드 주소 ↓ : springboot 주소(컨트롤러 주소)
const baseURL = "http://localhost:8000/api";

// TODO : 웹토큰 가져오기(로컬스토리지) => user.accessToken
let user = JSON.parse(localStorage.getItem("user"));
// TODO : 백엔드로 웹토큰 전송
const token = { Authorization: "Bearer " + user?.accessToken };
// insert/update용
const token2 = { "Content-Type": "multipart/form-data", Authorization: "Bearer " + user?.accessToken };
// TODO : 전송 : axios.get("url", {headers : token}), axios.post("url", data, {headers : token})

// 전체조회 (페이징 O 현재 사용안하는 중)
const getAll = (searchKeyword, pageIndex, recordCountPerPage) => {
    return axios.get(baseURL+`/tour?searchKeyword=${searchKeyword}&pageIndex=${pageIndex}&recordCountPerPage=${recordCountPerPage}`,
    {headers : token});
};

// 전체조회(페이징 X) 
const getALLnp = (searchKeyword,view,pop) => {
    return axios.get(baseURL+`/tour?searchKeyword=${searchKeyword}&view=${view}&pop=${pop}`, {headers : token});
};

// 이미지 업로드 및 FormData 생성
const insertForm = (data) => {
    let formData = new FormData();
    formData.append("name", data.name);
    formData.append("comment", data.comment);
    formData.append("location", data.location);
    formData.append("view", data.view);
    formData.append("category", data.category);
    formData.append("pop", data.pop);
    
    // 이미지가 있을 경우만 추가
    if (data.image) {
        formData.append("image", data.image);
    }

    return formData;
};

// 추가 및 업로드
const insert = (data) => {
    let form = insertForm(data);
    return axios.post(baseURL+"/tour/add", form, {headers : token2});
};

// 상세조회
const get = (tourId) => {
    return axios.get(baseURL+`/tour/get/${tourId}`, {headers : token});
};

// 수정
const update = (tourId, data) => {
    let form = insertForm(data); 
    return axios.put(baseURL+`/tour/update/${tourId}`, form, {headers : token2});
};

// 삭제
const remove = (tourId) => {
    return axios.delete(baseURL+`/tour/deletion/${tourId}`, {headers : token});
};

// 기본키 가져오기
const getTourId = () => {
    return axios.get(baseURL+`/review/tourId`, {headers : token})
}

const getName = (tourId) => {
    return axios.get(baseURL+`/tour/tourname/${tourId}`, {headers : token})
}

// 최저값 가져오기
const getRoomMinPrice = (tourId) => {
    return axios.get(baseURL+`/tour/minprice/${tourId}`, {headers : token})
}

const MainService = {
    getAll,
    insert,
    get,
    update,
    remove,
    getALLnp,
    getTourId,
    getName,
    getRoomMinPrice
};

export default MainService;