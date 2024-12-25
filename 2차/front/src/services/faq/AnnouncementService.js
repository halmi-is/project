import axios from "axios";

const baseURL = "http://localhost:8000/api/announcement";



let user = JSON.parse(localStorage.getItem("user"));
// 조회용
const token = { Authorization: "Bearer " + user?.accessToken };
// insert/update 용
// const token2 = {
//   "Content-Type": "multipart/form-data",
//   Authorization: "Bearer " + user?.accessToken,
// };






// 공지 전체 목록 조회
const getAll = (searchKeyword, pageIndex, recordCountPerPage) => {

  return axios.get(
    `${baseURL}?searchKeyword=${searchKeyword}&pageIndex=${pageIndex}&recordCountPerPage=${recordCountPerPage}`
    , {headers : token} );
};

// 특정 공지 상세 조회
const get = (ano) => {
  return axios.get(`${baseURL}/${ano}`
    , {headers : token}
  );
};

// 공지 수정
const update = (ano, data) => {
  return axios.put(baseURL+`/fix/${ano}`, data, {headers : token});
};

// 공지 삭제
const remove = (ano) => {
  return axios.delete(`${baseURL}/fix/${ano}`
    , {headers : token}
  );
};


const insert = (data) => {
  return axios.post(baseURL+`/add`, data, {headers : token});
};


// 서비스 객체
const AnnouncementService = {
  getAll,
  get,
  update,
  remove,
  insert,
};

export default AnnouncementService;