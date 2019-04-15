export default {
    state: {
        cartChosenItem: null,
    },
    mutations: {
        setCartChosen(state, cartChosenItem) {
            state.cartChosenItem = cartChosenItem;
        }
    },
    getters: {
        amount: state => {
            let amount = 0;
            if (state.cartChosenItem == null) {
                return 0;
            }
            for (let item of state.cartChosenItem) {
                amount += item.count * item.price;
            }
            return amount;
        }
    }
};
