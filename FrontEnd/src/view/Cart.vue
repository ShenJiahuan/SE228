<template>
    <div>
        <el-table
                :data="tableData"
                style="width: 100%" v-if="tableData != null && tableData.length > 0"
                @sort-change="this.sort">
            <el-table-column
                    label="选定"
                    min-width="10%"
                    align="center">
                <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.choose" @change="handleChange"></el-checkbox>
                </template>
            </el-table-column>
            <el-table-column
                    label="图片"
                    min-width="15%"
                    align="center">
                <template slot-scope="scope">
                    <img :src="scope.row.img" class="cart-img"/>
                </template>
            </el-table-column>
            <el-table-column
                    label="标题"
                    min-width="25%"
                    align="left">
                <template slot-scope="scope">
                    <router-link :to="'/info/' + scope.row.id">{{scope.row.title}}</router-link>
                </template>
            </el-table-column>
            <el-table-column
                    label="单价"
                    min-width="10%"
                    align="center"
                    sortable="custom">
                <template slot-scope="scope">
                    <div>¥{{scope.row.price.toFixed(2)}}</div>
                </template>
            </el-table-column>
            <el-table-column
                    label="数量"
                    min-width="15%"
                    align="center">
                <template slot-scope="scope">
                    <order-count-box
                        :num.sync="scope.row.count"
                        class="count"
                        size="mini"
                        :set-chosen="setChosen"
                        min=1
                        max=999
                        :book-id="scope.row.id">
                    </order-count-box>
                </template>
            </el-table-column>
            <el-table-column
                    label="金额"
                    min-width="15%"
                    align="center"
                    sortable="custom">
                <template slot-scope="scope">
                    <div>¥{{(scope.row.price * scope.row.count).toFixed(2)}}</div>
                </template>
            </el-table-column>
            <el-table-column
                    label="操作"
                    min-width="10%"
                    align="center">
                <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-delete" circle  v-on:click="remove(scope.row.id, true)"></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-card v-else-if="tableData != null">
            <h1>
                购物车是空的
            </h1>
        </el-card>
        <div class="sum">
            <div class="total-price">
                总金额：¥{{this.amount.toFixed(2)}}
            </div>
            <el-button type="primary" class="pay" :disabled="this.amount === 0" @click="pay">支付</el-button>
        </div>

    </div>

</template>

<script>
    import Api from "@/components/Api.js";
    import OrderCountBox from "@/components/OrderCountBox";
    import { Loading } from "element-ui";

    export default {
        name: "Cart",
        components: {OrderCountBox},
        data() {
            return {
                tableData: null,
            }
        },
        computed: {
            amount() {
                return this.$store.getters.amount;
            },
            username() {
                return this.$store.state.user.username;
            }
        },
        watch: {
            username() {
                this.getCart();
            }
        },
        methods: {
            remove(id, del) {
                for (let i = 0; i < this.tableData.length; ++i) {
                    if (this.tableData[i].id === id) {
                        let data = {
                            orders: [
                                {
                                    id: id,
                                    count: 0,
                                }
                            ]
                        };
                        console.log(data);
                        if (del) {
                            Api.DeleteOrder(data).then(
                                response => {
                                    this.$notify({
                                        title: "成功",
                                        message: "删除书籍成功",
                                        type: "success"
                                    });
                                }, error => {
                                    this.$notify({
                                        title: "错误",
                                        message: "未知错误",
                                        type: "error"
                                    });
                                }
                            );
                        }
                        this.tableData.splice(i, 1);
                        break;
                    }
                }
                this.setChosen();
            },
            sort(stat) {
                if (stat.column == null) {
                    this.tableData = this.tableData.sort((book1, book2) => book1.time > book2.time ? -1 : 1);
                } else if (stat.column.label === "金额") {
                    if (stat.order === "ascending") {
                        this.tableData = this.tableData.sort((book1, book2) => book1.price * book1.count < book2.price * book2.count ? -1 : 1);
                    } else {
                        this.tableData = this.tableData.sort((book1, book2) => book1.price * book1.count > book2.price * book2.count ? -1 : 1);
                    }
                } else if (stat.column.label === "单价") {
                    if (stat.order === "ascending") {
                        this.tableData = this.tableData.sort((book1, book2) => book1.price < book2.price ? -1 : 1);
                    } else {
                        this.tableData = this.tableData.sort((book1, book2) => book1.price > book2.price ? -1 : 1);
                    }
                }
            },
            setChosen() {
                let chosen = this.tableData.filter(item => {
                    return item.choose;
                });
                this.$store.commit("setCartChosen", chosen);
            },
            handleChange() {
                this.setChosen();
            },
            pay() {
                let books = this.$store.state.cartChosenStore.cartChosenItem;
                console.log(books);
                let data = {
                    orders: []
                };
                for (let book of books) {
                    data.orders.push({
                        id: book.id,
                        count: book.count
                    });
                }
                Api.UpdateOrder(data, true).then(
                    response => {
                        this.$notify({
                            title: "成功",
                            message: "支付成功",
                            type: "success"
                        });
                        for (let item of data.orders) {
                            this.remove(item.id, false);
                        }
                    }, error => {
                        switch (error.response.data.status) {
                            case 400:
                                this.$notify({
                                    title: "错误",
                                    message: "库存不足",
                                    type: "error"
                                });
                                break;
                            default:
                                this.$notify({
                                    title: "错误",
                                    message: "未知错误",
                                    type: "error"
                                });
                                break;
                        }
                    }
                );
            },
            getCart() {
                Api.GetOrder(false).then(
                    response => {
                        let dateFormat = require('dateformat');
                        let result = response.data;
                        this.tableData = [];
                        if (result != null) {
                            for (let item of result) {
                                let time = new Date(item[0].purchaseTime * 1000);
                                this.tableData.push({
                                    id: item[1].bookId,
                                    choose: false,
                                    img: this.$store.state.config.backend + "images/" + item[1].img,
                                    title: item[1].title,
                                    price: item[1].price,
                                    count: item[0].count,
                                    time: dateFormat(time, "yyyy-mm-dd HH:MM:ss"),
                                })
                            }
                        }
                        if (result != null) {
                            this.setChosen();
                        }
                    }, error => {
                        switch (error.response.data.status) {
                            case 401:
                                this.$notify.error({
                                    title: "错误",
                                    message: "请先登录"
                                });
                                this.$router.push({path: '/login', query: {redirect: this.$route.fullPath}});
                                break;
                            case 403:
                                this.$notify.error({
                                    title: "错误",
                                    message: "您的账户已被禁用，请联系管理员"
                                });
                                this.$router.push("/");
                                break;
                            default:
                                this.$notify({
                                    title: "错误",
                                    message: "未知错误",
                                    type: "error"
                                });
                                break;
                        }
                    }
                );
            }
        },
        created() {
            this.getCart();
        }
    }
</script>

<style scoped>
    .cart-img {
        height: 120px;
        text-align: center;
        border-radius: 2px;
    }



    .total-price {
        font-size: 18px;
        font-weight: bold;
    }

    .sum {
        margin-top: 10px;
        float: right;
        display: flex;
        align-items: center;
    }

    .pay {
        margin: 0 20px;
    }
</style>
