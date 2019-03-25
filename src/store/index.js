import Vue from 'vue'
import vuex from 'vuex'
Vue.use(vuex);

import user_store from '../components/user_store.js';

export default new vuex.Store({
    modules: {
        user: user_store,
    }
})