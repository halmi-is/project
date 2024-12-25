import axios from "axios";

const baseURL = "http://localhost:8000/api";

// TODO : 웹토큰 가져오기(로컬스토리지) => user.accessToken
let user = JSON.parse(localStorage.getItem("user"));
// TODO : 백엔드로 웹토큰 전송
const token = { Authorization: "Bearer " + user?.accessToken };
// insert/update용
// const token2 = { "Content-Type": "multipart/form-data", Authorization: "Bearer " + user?.accessToken };
// TODO : 전송 : axios.get("url", {headers : token}), axios.post("url", data, {headers : token})

// 장바구니에 추가
const addToCart = (data) => {
  return axios.post(baseURL + `/cart/add`, data, {headers : token});
};

// 이메일마다 장바구니 전체조회
const getItemsFromEmail = (userEmail) => {
  return axios.get(baseURL + `/cart/detail/${userEmail}`, {headers : token});
};

// 장바구니 개별 삭제
const deleteFromCartId = (cartId) => {
  return axios.delete(baseURL + `/cart/delete/${cartId}`, {headers : token});
};

// 장바구니 전체 삭제
const deleteFromCartEmail = (userEmail) => {
  return axios.delete(baseURL + `/cart/delete/all/${userEmail}`, {headers : token});
};

// 장바구니 개수 카운트
const countCartItems = (userEmail) => {
  return axios.get(baseURL + `/cart/count/${userEmail}`, {headers : token});
};

// cartId로 장바구니 상세조회
const getItemsFromCartId = (cartId) => {
  return axios.get(baseURL + `/cart/detail/item/${cartId}`, {headers : token});
};

const CartService = {
  addToCart,
  getItemsFromEmail,
  deleteFromCartId,
  deleteFromCartEmail,
  countCartItems,
  getItemsFromCartId,
};

export default CartService;
