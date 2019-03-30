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
            for (let i in state.cartChosenItem) {
                let item = state.cartChosenItem[i];
                amount += item.count * item.price;
            }
            return amount;
        }
    }
};