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
        login(state, username, admin) {
            state.username = username;
            state.admin = admin;
        }
    }
}