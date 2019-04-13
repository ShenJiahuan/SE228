export default {
    state: {
        username: null,
        admin: false,
    },
    mutations: {
        logout(state) {
            state.username = null;
            state.admin = false;
        },
        login(state, username, email, admin) {
            state.username = username;
            state.admin = admin;
        }
    }
};
