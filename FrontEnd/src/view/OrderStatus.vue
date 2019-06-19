<template>
    <div>
        <el-date-picker
                v-model="value"
                type="daterange"
                :picker-options="pickerOptions"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                align="right">
        </el-date-picker>

        <el-table
                :data="tableData"
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
</template>

<script>
    import Api from "@/components/Api.js";

    export default {
        name: "OrderStatus",
        data() {
            return {
                tableData: [],
                value: null,
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
                this.getOrderStatus();
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
                        this.tableData = [];
                        for (let item of response.data) {
                            this.tableData.push({
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
            }
        }
    }
</script>

<style scoped>
    .order-stat-img {
        height: 120px;
        text-align: center;
        border-radius: 2px;
    }
</style>
