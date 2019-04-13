import axios from "axios";

axios.defaults.baseURL = "http://192.168.1.103:8080";
axios.defaults.withCredentials = true;

export default {
    GetBookInfo(id) {
        return axios.get("/books/" + id);
    },

    GetBookList(keyword) {
        return axios.get("/books", {params: {keyword: keyword}});
    },

    GetRecommendList(limit) {
        return axios.get("/books/recommend",  {params: {limit: limit}});
    },

    GetHotList(limit) {
        return axios.get("/books/hot", {params: {limit: limit}} );
    },

    Login(email, password) {
        var params = new URLSearchParams();
        params.append("username", email);
        params.append("password", password);
        return axios.post("/login", params);
    }
};
