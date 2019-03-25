<template>
    <el-table
            :data="tableData"
            style="width: 100%" v-if="tableData.length > 0"
            @sort-change="this.sort">
        <el-table-column
                label="选定"
                min-width="10%"
                align="center">
            <template slot-scope="scope">
                <el-checkbox v-model="scope.row.choose"></el-checkbox>
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
                prop="title"
                label="标题"
                min-width="30%"
                align="left">
        </el-table-column>
        <el-table-column
                label="单价"
                min-width="10%"
                align="center"
                sortable="custom">
            <template slot-scope="scope">
                <div>¥{{scope.row.price}}</div>
            </template>
        </el-table-column>
        <el-table-column
                label="数量"
                min-width="15%"
                align="center">
            <template slot-scope="scope">
                <order-count-box :num.sync="scope.row.count" class="count"></order-count-box>
            </template>
        </el-table-column>
        <el-table-column
                label="金额"
                min-width="10%"
                align="center"
                sortable="custom">
            <template slot-scope="scope">
                <div>¥{{scope.row.price * scope.row.count}}</div>
            </template>
        </el-table-column>
        <el-table-column
                label="操作"
                min-width="10%"
                align="center">
            <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-delete" circle  v-on:click="remove(scope.row)"></el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-card v-else>
        <h1>
            购物车是空的
        </h1>
    </el-card>
</template>

<script>
    import OrderCountBox from "@/components/OrderCountBox";
    export default {
        name: "Cart",
        components: {OrderCountBox},
        data() {
            return {
                tableData: [{
                    id: 1,
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "我再重构我是狗",
                    price: 30.00,
                    count: 2,
                    time: 1,
                }, {
                    id: 2,
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "Element-UI有点意思",
                    price: 20.00,
                    count: 2,
                    time: 2,
                }, {
                    id: 3,
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "重构！",
                    price: 10.00,
                    count: 2,
                    time: 3,
                }, {
                    id: 4,
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "真香。",
                    price: 40.00,
                    count: 2,
                    time: 4,
                }]
            }
        },
        methods: {
            remove(book) {
                for (let i = 0; i < this.tableData.length; ++i) {
                    console.log(this.tableData[i].id, book.id);
                    if (this.tableData[i].id === book.id) {
                        console.log("11111");
                        this.tableData.splice(i, 1);
                        break;
                    }
                }
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
            }
        },
        created() {
            if (this.$store.state.user.username == null) {
                this.$notify.error({
                    title: '错误',
                    message: '请先登录'
                });
                this.$router.push('/login');
            }
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