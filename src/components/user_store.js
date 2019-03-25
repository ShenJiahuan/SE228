export default {
    state: {
        username: "沈嘉欢",
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