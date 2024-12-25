import axios from "axios";

const baseURL = "http://localhost:8000/api";

const getAll = (searchKeyword, pageIndex, recordCountPerPage) => {
    return axios.get(
    `${baseURL}/faq/list?searchKeyword=${searchKeyword}&pageIndex=${pageIndex}&recordCountPerPage=${recordCountPerPage}`
);
};

const getDetail = (fno) => {
    return axios.get(baseURL+`/faq/list/${fno}`);
}


const FaqService = {
    getAll,
    getDetail,
};

export default FaqService;