import axios from "axios"

const baseURL = "http://localhost:8000/api";

// TODO : 웹토큰 가져오기(로컬스토리지) => user.accessToken
let user = JSON.parse(localStorage.getItem("user"));
// insert/update용 백엔드로 웹토큰 전송
const token = {  Authorization: "Bearer " + user?.accessToken };
// TODO : 전송 : axios.get("url", {headers : token}), axios.post("url", data, {headers : token})

// data를 JSON 형식으로 변환하는 함수
const insertForm = (data) => {
    return JSON.stringify(data);
  };

const notified = (data) =>{
    let from = insertForm(data);
    console.log(from);
    return axios.post(baseURL+"/payment/complete",from,{
        headers:{
            'Content-Type': 'application/json' //json 형식으로 전송
        }
    }, {headers : token});
};

const TossService = {
    notified,
}

export default TossService;