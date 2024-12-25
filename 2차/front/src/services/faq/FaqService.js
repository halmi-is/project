import axios from "axios";

const baseURL = "http://localhost:8000/api";

const getAll = (searchKeyword, pageIndex, recordCountPerPage) => {
  return axios.get(
    `${baseURL}/faq/list?searchKeyword=${searchKeyword}&pageIndex=${pageIndex}&recordCountPerPage=${recordCountPerPage}`
  );
};

const get = (fno) => {
  return axios.get(baseURL + `/faq/list/${fno}`);
};

const update = (fno, data) => {
  return axios.put(baseURL + `/faq/list/update/${fno}`, data);
};

const remove = (fno) => {
  return axios.delete(baseURL + `/faq/list/delete/${fno}`);
};


const insert = (data) => {
  return axios.post(baseURL + `/faq/add`, data, );
};


const FaqService = {
  getAll,
  get,
  update,
  remove,
  insert
};

export default FaqService;
