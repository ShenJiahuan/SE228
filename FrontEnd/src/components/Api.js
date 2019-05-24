import axios from "axios";
import { Loading } from "element-ui";
import store from "../store";

axios.defaults.baseURL = store.state.config.backendServer;
axios.defaults.withCredentials = true;

let loadingInstance = null;

axios.interceptors.request.use(
    config => {
        loadingInstance = Loading.service({ fullscreen: true });
        return config;
    },
    error =>{
        loadingInstance.close();
        return Promise.reject(error);
});

axios.interceptors.response.use(
    response => {
        loadingInstance.close();
        return response;
    },
    error => {
        loadingInstance.close();
        return Promise.reject(error);
});

export default {
    GetBookInfo(id) {
        return axios.get("/books/" + id);
    },

    GetBookList(keyword) {
        return axios.get("/books/", {params: {keyword: keyword}});
    },

    GetRecommendList(limit) {
        return axios.get("/books/top/recommend",  {params: {limit: limit}});
    },

    GetHotList(limit) {
        return axios.get("/books/top/hot", {params: {limit: limit}} );
    },

    CreateBook(info) {
        return axios.post("/books", info);
    },

    UpdateBook(info) {
        return axios.put("/books", info);
    },

    DeleteBook(id) {
        return axios.delete("/books/" + id);
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

    GetOrder() {
        return axios.get("/orders");
    },

    CreateOrder(order) {
        return axios.post("/orders", order);
    },

    CartToOrder(order) {
        return axios.put("/orders", order);
    },

    GetCart() {
        return axios.get("/cart");
    },

    CreateCart(order) {
        return axios.post("/cart", order);
    },

    UpdateCart(order) {
        return axios.put("/cart", order);
    },

    DeleteCart(order) {
        return axios.delete("/cart", {data: order});
    },

    GetAllUser() {
        return axios.get("/user/list");
    },

    BanUser(uid, ban) {
        return axios.post("/user/ban", {}, {params: {uid: uid, ban: ban}});
    },

    AdminUser(uid, admin) {
        return axios.post("/user/admin", {}, {params: {uid: uid, admin: admin}});
    }
};
