<template>
    <div>
        <PurchasedDatePicker></PurchasedDatePicker>
        <el-table
                :data="tableData"
                style="width: 100%" v-if="tableData != null && tableData.length > 0">
            <el-table-column
                    label="图片"
                    min-width="12%"
                    align="center">
                <template slot-scope="scope">
                    <img :src="scope.row.img" class="cart-img"/>
                </template>
            </el-table-column>
            <el-table-column
                    prop="title"
                    label="标题"
                    min-width="14%"
                    align="left"
                    :filters="this.titleFilter"
                    :filter-method="this.filterHandler"
                    >
            </el-table-column>
            <el-table-column
                    label="单价"
                    min-width="10%"
                    align="center">
                <template slot-scope="scope">
                    <div>¥{{scope.row.price.toFixed(2)}}</div>
                </template>
            </el-table-column>
            <el-table-column
                    prop="count"
                    label="数量"
                    min-width="9%"
                    align="center">
            </el-table-column>
            <el-table-column
                    label="金额"
                    min-width="12%"
                    align="center">
                <template slot-scope="scope">
                    <div>¥{{(scope.row.price * scope.row.count).toFixed(2)}}</div>
                </template>
            </el-table-column>
            <el-table-column
                    prop="time"
                    label="购买时间"
                    min-width="20%"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="orderId"
                    label="订单编号"
                    min-width="12%"
                    align="center"
                    :filters="this.orderIdFilter"
                    :filter-method="this.filterHandler"
                    >
            </el-table-column>
            <el-table-column
                    prop="userId"
                    label="用户 ID"
                    min-width="11%"
                    align="left"
                    :filters="this.userIdFilter"
                    :filter-method="this.filterHandler"
                    >
            </el-table-column>
        </el-table>
        <el-card v-else-if="tableData != null">
            <h1>
                该时间段内无购买记录
            </h1>
        </el-card>
    </div>
</template>

<script>
    import Api from "@/components/Api.js";
    import PurchasedDatePicker from "@/components/PurchasedDatePicker";
    import { Loading } from "element-ui";

    export default {
        name: "Purchased",
        components: {PurchasedDatePicker},
        data() {
            return {
                initialData: null,
            }
        },
        computed: {
            time() {
                return this.$store.state.searchTime.time;
            },
            tableData() {
                if (this.initialData == null) {
                    return null;
                }
                return this.initialData.filter(book => {
                    let year = parseInt(book.time.substr(0, 4));
                    let month = parseInt(book.time.substr(5, 6)) - 1;
                    let day = parseInt(book.time.substr(8, 9));
                    let orderTime = new Date(year, month, day);
                    if (this.time.minDate === null || this.time.maxDate === null) {
                        return false;
                    }

                    return orderTime.getTime() >= this.time.minDate.getTime() &&
                            orderTime.getTime() <= this.time.maxDate.getTime();
                })
            },
            username() {
                return this.$store.state.user.username;
            },
            titleFilter() {
                let result = [];
                for (let row of this.tableData) {
                    if (this.exist(result, {text: row.title, value: row.title})) {
                        continue;
                    }
                    result.push({text: row.title, value: row.title});
                }
                return result;
            },
            orderIdFilter() {
                let result = [];
                for (let row of this.tableData) {
                    if (this.exist(result, {text: row.orderId, value: row.orderId})) {
                        continue;
                    }
                    result.push({text: row.orderId, value: row.orderId});
                }
                return result;
            },
            userIdFilter() {
                let result = [];
                for (let row of this.tableData) {
                    if (this.exist(result, {text: row.userId, value: row.userId})) {
                        continue;
                    }
                    result.push({text: row.userId, value: row.userId});
                }
                return result;
            }
        },
        watch: {
            username() {
                this.getPurchased();
            }
        },
        created() {
            this.getPurchased();
        },
        methods: {
            getPurchased() {
                Api.GetAllOrders().then(
                    response => {
                        console.log(response);
                        let dateFormat = require('dateformat');
                        let result = response.data;
                        this.initialData = [];
                        if (result != null) {
                            for (let order of result) {
                                let time = new Date(order.payTime * 1000);
                                for (let item of order.items) {
                                    let parsed_item = {
                                        id: item.snapshot.bookId,
                                        choose: false,
                                        title: item.snapshot.title,
                                        price: item.snapshot.price,
                                        count: item.count,
                                        time: dateFormat(time, "yyyy-mm-dd HH:MM:ss"),
                                        orderId: order.orderId,
                                        userId: order.uid,
                                    };
                                    parsed_item.img = this.$store.state.config.backendServer + "books/" + item.snapshot.bookId + "/image";
                                    this.initialData.push(parsed_item);
                                }
                            }
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
                )
            },
            filterHandler(value, row, column) {
                const property = column['property'];
                return row[property] === value;
            },
            exist(list, obj) {
                for (let item of list) {
                    if (JSON.stringify(item) === JSON.stringify(obj)) {
                        return true;
                    }
                }
                return false;
            },
        }
    }
</script>

<style scoped>
    .cart-img {
        height: 120px;
        text-align: center;
        border-radius: 2px;
    }

    .count {
        width: 60px;
    }
</style>
