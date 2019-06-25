<template>
    <div>
        <div class="option-group">
            <div>
                <el-radio-group v-model="bookOrUser" class="option">
                    <el-radio-button label="书籍"></el-radio-button>
                    <el-radio-button label="用户"></el-radio-button>
                </el-radio-group>
            </div>
            <el-date-picker
                    v-model="value"
                    type="daterange"
                    :picker-options="pickerOptions"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    align="right"
                    class="option">
            </el-date-picker>
        </div>
        <div v-show="bookOrUser === '书籍'">
            <el-table
                    id="book-table"
                    :data="tableData1"
                    style="width: 100%">
                <el-table-column
                        label="图片"
                        min-width="15%"
                        align="center">
                    <template slot-scope="scope">
                        <img :src="scope.row.img" class="order-stat-img"/>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="title"
                        label="标题"
                        min-width="18%"
                        align="left">
                </el-table-column>
                <el-table-column
                        prop="author"
                        label="作者"
                        min-width="22%"
                        align="left">
                </el-table-column>
                <el-table-column
                        prop="isbn"
                        label="ISBN"
                        min-width="20%"
                        align="left">
                </el-table-column>
                <el-table-column
                        prop="remain"
                        label="库存"
                        min-width="10%"
                        align="left">
                </el-table-column>
                <el-table-column
                        prop="sell"
                        label="销量"
                        min-width="15%"
                        align="center">
                </el-table-column>
            </el-table>
        </div>
        <div v-show="bookOrUser === '用户'">
            <el-table
                    id="user-table"
                    :data="tableData2"
                    style="width: 100%">
                <el-table-column
                        prop="username"
                        label="用户名"
                        min-width="30%"
                        align="left">
                </el-table-column>
                <el-table-column
                        prop="email"
                        label="邮箱"
                        min-width="40%"
                        align="left">
                </el-table-column>
                <el-table-column
                        label="消费金额"
                        min-width="30%"
                        align="left">
                    <template slot-scope="scope">
                        <div>¥{{scope.row.amount.toFixed(2)}}</div>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    import Api from "@/components/Api.js";

    export default {
        name: "OrderStatus",
        data() {
            return {
                tableData1: [],
                tableData2: [],
                value: null,
                bookOrUser: "书籍",
                pickerOptions: {
                    shortcuts: [{
                        text: "最近一周",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit("pick", [start, end]);
                        }
                    }, {
                        text: "最近一个月",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit("pick", [start, end]);
                        }
                    }, {
                        text: "最近三个月",
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit("pick", [start, end]);
                        }
                    }]
                },
            }
        },
        created() {
            const end = new Date();
            const start = new Date();
            start.setDate(start.getDate() - 30);
            start.setHours(0, 0, 0);
            end.setHours(0, 0, 0);
            this.value = [start, end];
            this.getOrderStatus();
        },
        watch: {
            value() {
                console.log("update");
                this.getOrderStatus();
                this.getUserPurchase();
            }
        },
        methods: {
            getOrderStatus() {
                this.value[1].setHours(23, 59, 59);
                let from = this.value[0].getTime() / 1000;
                let to = this.value[1].getTime() / 1000;
                Api.GetOrderStatus(from, to).then(
                    response => {
                        console.log(response);
                        this.tableData1 = [];
                        for (let item of response.data) {
                            this.tableData1.push({
                                img: this.$store.state.config.backendServer + "books/" + item[0].bookId + "/image",
                                title: item[0].title,
                                author: item[0].author,
                                isbn: item[0].isbn,
                                remain: item[0].remain,
                                sell: item[1]
                            });
                        }
                    }
                )
            },

            getUserPurchase() {
                this.value[1].setHours(23, 59, 59);
                let from = this.value[0].getTime() / 1000;
                let to = this.value[1].getTime() / 1000;
                Api.GetUserPurchase(from, to).then(
                    response => {
                        console.log(response);
                        this.tableData2 = [];
                        for (let item of response.data) {
                            this.tableData2.push({
                                username: item[0],
                                email: item[1],
                                amount: item[2]
                            });
                        }
                    }
                )
            },
        }
    }
</script>

<style scoped>
    .option-group {
        display: flex;
        justify-content: center;
        margin-bottom: 20px;
    }

    .option {
        margin: 0 20px;
    }

    .order-stat-img {
        height: 120px;
        text-align: center;
        border-radius: 2px;
    }
</style>
