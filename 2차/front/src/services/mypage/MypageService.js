import axios from "axios";
// 백엔드 주소 ↓ : springboot 주소(컨트롤러 주소)
const baseURL = "http://localhost:8000/api";

let user = JSON.parse(localStorage.getItem("user"));
// 조회용
const token = { Authorization: "Bearer " + user?.accessToken };
// insert/update 용
// const token2 = {
//   "Content-Type": "multipart/form-data",
//   Authorization: "Bearer " + user?.accessToken,
// };

const getAll = (searchKeyword, pageIndex, recordCountPerPage) => {
  return axios.get(
    baseURL +
      `/mypage?searchKeyword=${searchKeyword}&pageIndex=${pageIndex}&recordCountPerPage=${recordCountPerPage}`,
    {
      headers: token,
    }
  );
};

const MypageService = {
  getAll,
};

export default MypageService;
