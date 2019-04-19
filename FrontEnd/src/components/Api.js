import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.withCredentials = true;

export default {
    GetBookInfo(id) {
        return axios.get("/books/" + id);
    },

    GetBookList(keyword) {
        return axios.get("/books/", {params: {keyword: keyword}});
    },

    GetRecommendList(limit) {
        return axios.get("/books/recommend",  {params: {limit: limit}});
    },

    GetHotList(limit) {
        return axios.get("/books/hot", {params: {limit: limit}} );
    },

    Login(email, password) {
        var params = new URLSearchParams();
        params.append("email", email);
        params.append("password", password);
        return axios.post("/user/login", params);
    },

    Register(email, username, password) {
        var params = new URLSearchParams();
        params.append("email", email);
        params.append("username", username);
        params.append("password", password);
        return axios.post("/user/register", params);
    },

    Logout() {
        return axios.get("/user/logout");
    },

    GetUsername() {
        return axios.get("/user");
    },

    GetOrder(paid) {
        return axios.get("/orders", {params: {paid: paid}});
    },

    CreateOrder(order, paid) {
        return axios.post("/orders", order, {params: {paid: paid}});
    },

    UpdateOrder(order) {
        return axios.put("/orders", order);
    },

    DeleteOrder(order) {
        return axios.delete("/orders", {data: order});
    }
};
