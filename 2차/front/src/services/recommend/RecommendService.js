import axios from "axios";
// 백엔드 주소 ↓ : springboot 주소(컨트롤러 주소)
const baseURL = "http://localhost:8000/api";

let user = JSON.parse(localStorage.getItem("user"));
// 조회용
const token = { Authorization: "Bearer " + user?.accessToken };
// insert/update 용
const token2 = {
  "Content-Type": "multipart/form-data",
  Authorization: "Bearer " + user?.accessToken,
};

// 전체조회다
const getAll = (searchKeyword, pageIndex, recordCountPerPage) => {
  return axios.get(
    baseURL +
      `/recommend?searchKeyword=${searchKeyword}&pageIndex=${pageIndex}&recordCountPerPage=${recordCountPerPage}`,
    { headers: token }
  );
};

// 상세 조회 : 기본키(uuid)
const get = (tdId) => {
  return axios.get(baseURL + `/recommend/get/${tdId}`, {
    headers: token,
  });
};

// 삭제
const remove = (tdId) => {
  return axios.delete(baseURL + `/recommend/deletion/${tdId}`, {
    headers: token2,
  });
};

// 수정
const update = (tdId, data) => {
  let form = insertForm(data);
  return axios.put(baseURL + `/recommend/update/${tdId}`, form, {
    headers: token2,
  });
};

// 생성
const insert = (data) => {
  let form = insertForm(data);
  return axios.post(baseURL + "/recommend/add", form, {
    headers: token2,
  });
};

const insertForm = (data) => {
  console.log(data);
  // FormData 객체 생성
  let formData = new FormData();
  // FormData 객체에 넣기 : .append(키(벡엔드변수명), 값)
  formData.append("tdName", data.tdName);
  formData.append("loc", data.loc);
  formData.append("description", data.description);
  formData.append("image", data.image);

  return formData;
};

const RecommendService = {
  getAll,
  get,
  update,
  insert,
  remove,
};

export default RecommendService;
