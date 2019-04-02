export default {
    state: {
        time: {
            minDate: null,
            maxDate: null
        }

    },
    mutations: {
        setTime(state, time) {
            state.time = time;
        }
    }
};