export default {
    state: {
        username: null,
        admin: false,
        root: false,
    },
    mutations: {
        logout(state) {
            state.username = "";
            state.admin = false;
            state.root = false;
        },
        login(state, payload) {
            state.username = payload.username;
            state.admin = payload.admin;
            state.root = payload.root;
        }
    }
};
