import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.withCredentials = true;

export default {
    GetBookInfo(params) {
        return axios.get("/bookinfo", {params: params});
    },

    GetBookList(params) {
        return axios.get("/booklist", {params: params});
    },

    GetRecommendList() {
        return axios.get("/recommend");
    },

    GetHotList() {
        return axios.get("/hot");
    }
};
