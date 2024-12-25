import axios from "axios";

const baseURL = "http://localhost:8000/api";

// TODO : 웹토큰 가져오기(로컬스토리지) => user.accessToken
let user = JSON.parse(localStorage.getItem("user"));
// TODO : 백엔드로 웹토큰 전송
const token = { Authorization: "Bearer " + user?.accessToken };
// insert/update용
const token2 = {
  "Content-Type": "multipart/form-data",
  Authorization: "Bearer " + user?.accessToken,
};
// TODO : 전송 : axios.get("url", {headers : token}), axios.post("url", data, {headers : token})

// 투어 ID로 방 목록 가져오기
const getRoomsByTourId = (tourId) => {
  return axios.get(baseURL + `/room/tour/${tourId}`, { headers: token });
};

// 방 1개 상세조회
const DetailRoom = (roomId) => {
  return axios.get(baseURL + `/room/detail/${roomId}`, { headers: token });
};

const insertForm = (data) => {
  let formData = new FormData();
  formData.append("tourId", data.tourId); // tourId 추가
  formData.append("roomName", data.roomName);
  formData.append("location", data.location);
  formData.append("description", data.description);
  formData.append("price", data.price);
  formData.append("capacity", data.capacity);
  formData.append("checkIn", data.checkIn);
  formData.append("checkOut", data.checkOut);
  formData.append("image", data.image);

  return formData;
};

// 방 추가
const addRoom = (data) => {
  let form = insertForm(data);

  return axios.post(baseURL + `/room/add`, form, { headers: token2 });
};

// 방 업데이트
const UpdateRoom = (roomId, roomData) => {
  let form = insertForm(roomData);

  return axios.put(baseURL + `/room/update/${roomId}`, form, { headers: token2 });
};

// 방 삭제
const RemoveRoom = (roomId) => {
  return axios.delete(baseURL + `/room/delete/${roomId}`, { headers: token });
};

// insertForm2 - 방 이미지 추가용
const insertForm2 = (data) => {
  let formData = new FormData();
  formData.append("image", data.image);
  formData.append("roomId", data.roomId);

  return formData;
};

// 각 방에 사진 추가
const addRoomPhoto = (data) => {
  let form = insertForm2(data);

  return axios.post(baseURL + `/room/image/add`, form, { headers: token2 });
};

// 룸 ID로 이미지 경로 가져오기
const getUrlsByRoomId = (roomId) => {
  return axios.get(baseURL + `/room/image/url/${roomId}`, { headers: token });
};

const RoomService = {
  getRoomsByTourId,
  addRoom,
  UpdateRoom,
  RemoveRoom,
  DetailRoom,
  addRoomPhoto,
  getUrlsByRoomId,
};

export default RoomService;
