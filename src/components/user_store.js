export default {
    state: {
        username: null,
        email: null,
        admin: false,
    },
    mutations: {
        logout(state) {
            state.username = null;
            state.email = null;
            state.admin = false;
        },
        login(state, username, email, admin) {
            state.username = username;
            state.email = email;
            state.admin = admin;
        }
    }
}