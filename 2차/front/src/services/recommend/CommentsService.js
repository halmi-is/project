import axios from "axios";
// 백엔드 주소 ↓ : springboot 주소(컨트롤러 주소)
const baseURL = "http://localhost:8000/api";

let user = JSON.parse(localStorage.getItem("user"));
// 조회용
const token = { Authorization: "Bearer " + user?.accessToken };

// insert/update 용

const token2 = {
  "Content-Type": "application/json",
  Authorization: "Bearer " + user?.accessToken,
};

// 생성
const insert = (data) => {
  return axios.post(baseURL + `/comments/add`, data, {
    headers: token2,
  });
};

const getAll = (searchKeyword, pageIndex, recordCountPerPage) => {
  return axios.get(
    baseURL +
      `/comments?searchKeyword=${searchKeyword}&pageIndex=${pageIndex}&recordCountPerPage=${recordCountPerPage}`, {headers : token}
  );
};

const get = (comId) => {
  return axios.get(baseURL + `/comments/get/${comId}`, {headers : token});
};

/* TODO: 수정(dno) */

const update = (comId, data) => {
  return axios.put(baseURL + `/comments/update/${comId}`, data, {
    headers: token2,
  });
};

/* TODO: 삭제(dno) */

const remove = (comId) => {
  return axios.delete(baseURL + `/comments/deletion/${comId}`, {
    headers: token2,
  });
};

const CommentsService = {
  insert,
  getAll,
  get,
  update,
  remove,
};

export default CommentsService;
