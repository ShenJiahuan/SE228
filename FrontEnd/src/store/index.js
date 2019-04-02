import Vue from "vue";
import vuex from "vuex";
Vue.use(vuex);

import user_store from "../components/user_store.js";
import search_time_store from "../components/search_time_store.js";
import cart_chosen_store from "../components/cart_chosen_store.js";

export default new vuex.Store({
    modules: {
        user: user_store,
        searchTime: search_time_store,
        cartChosenStore: cart_chosen_store,
    }
});