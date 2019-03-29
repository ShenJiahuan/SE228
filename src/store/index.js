import Vue from "vue";
import vuex from "vuex";
Vue.use(vuex);

import user_store from "../components/user_store.js";
import search_time_store from "../components/search_time_store.js";

export default new vuex.Store({
    modules: {
        user: user_store,
        searchTime: search_time_store,
    }
});