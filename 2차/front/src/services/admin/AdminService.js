import axios from "axios";

const baseURL = "http://localhost:8080/api"

// TODO : 웹토큰 가져오기(로컬스토리지) => user.accessToken
let user = JSON.parse(localStorage.getItem("user"));
// TODO : 백엔드로 웹토큰 전송
const token = { Authorization: "Bearer " + user?.accessToken };
// insert/update용
const token2 = { "Content-Type": "multipart/form-data", Authorization: "Bearer " + user?.accessToken };
// TODO : 전송 : axios.get("url", {headers : token}), axios.post("url", data, {headers : token})

const getAll = (searchKeyword, pageIndex, recordCountPerPage) => {
    return axios
       .get(baseURL+`/admin/admin?searchKeyword=${searchKeyword}&pageIndex=${pageIndex}&recordCountPerPage=${recordCountPerPage}`,
        {headers : token}
       );
};

const insert = (data) => {
    return axios.post(baseURL+"/admin/admin", data, {headers : token2});
}

const get = (fno) => {
    return axios.get(baseURL+`/admin/admin/${fno}`, {headers : token});
}

const update = (fno, data)=>{
    return axios.put(baseURL+`/basic/admin/${fno}`,data, {headers : token2});
}


const remove = (fno)=>{
    return axios.delete(baseURL+`/admin/admin/deletion/${fno}`, {headers : token});
}

const AdminService = {
    getAll,
    insert,
    get,
    update,
    remove
}
export default AdminService;