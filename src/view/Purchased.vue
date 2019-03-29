<template>
    <el-table
            :data="tableData"
            style="width: 100%" v-if="tableData.length > 0">
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
                min-width="25%"
                align="left">
        </el-table-column>
        <el-table-column
                label="单价"
                min-width="10%"
                align="center">
            <template slot-scope="scope">
                <div>¥{{scope.row.price}}</div>
            </template>
        </el-table-column>
        <el-table-column
                prop="count"
                label="数量"
                min-width="15%"
                align="center">
        </el-table-column>
        <el-table-column
                label="金额"
                min-width="10%"
                align="center">
            <template slot-scope="scope">
                <div>¥{{scope.row.price * scope.row.count}}</div>
            </template>
        </el-table-column>
        <el-table-column
                prop="time"
                label="购买时间"
                min-width="25%"
                align="center">
        </el-table-column>
    </el-table>
    <el-card v-else>
        <h1>
            购物车是空的
        </h1>
    </el-card>
</template>

<script>
    export default {
        name: "Bought",
        data() {
            return {
                tableData: [{
                    id: 1,
                    img: require("@/static/1.jpg"),
                    title: "我再重构我是狗",
                    price: 30.00,
                    count: 2,
                    time: "2019-03-29 18:53:40",
                }, {
                    id: 2,
                    img: require("@/static/1.jpg"),
                    title: "Element-UI有点意思",
                    price: 20.00,
                    count: 2,
                    time: "2019-03-28 18:53:39",
                }, {
                    id: 3,
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "重构！",
                    price: 10.00,
                    count: 2,
                    time: "2019-03-27 18:53:38",
                }, {
                    id: 4,
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "真香。",
                    price: 40.00,
                    count: 2,
                    time: "2019-03-26 18:53:37",
                }]
            }
        },
        created() {
            if (this.$store.state.user.username == null) {
                this.$notify.error({
                    title: "错误",
                    message: "请先登录"
                });
                this.$router.push("/login");
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