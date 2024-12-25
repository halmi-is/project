import axios from "axios";
// 백엔드 주소 : 스트링부트 주소 (컨트롤러주소)
const baseURL = "http://localhost:8000/api";

let user = JSON.parse(localStorage.getItem("user"));
const token = { Authorization: "Bearer " + user?.accessToken };

const insert = (data) => {
  return axios.post(baseURL + `/add/coupon`, data, { headers: token });
};

const getAll = (searchKeyword, pageIndex, recordCountPerPage) => {
  return axios.get(
    baseURL +
      `/list/coupon?searchKeyword=${searchKeyword}&pageIndex=${pageIndex}&recordCountPerPage=${recordCountPerPage}`
  );
};

const getCouponsByEmail = () => {
  return axios.get(baseURL + `/use/coupon`, { headers: token });
};

const checkUserHasCoupons = () => {
  return axios.get(baseURL + `/user/hasCoupons`, { headers: token });
};

const couponByEmail = (memberEmail) => {
  console.log("memberEmail", memberEmail);
  return axios.get(baseURL + `/coupon/email/${memberEmail}`);
};

const CouponService = {
  insert,
  getAll,
  getCouponsByEmail,
  checkUserHasCoupons,
  couponByEmail,
};

export default CouponService;
