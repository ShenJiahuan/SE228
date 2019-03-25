<template>
    <el-table
            :data="tableData"
            style="width: 100%">
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
                align="center">
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
                align="center">
            <template slot-scope="scope">
                <div>¥{{scope.row.price * scope.row.count}}</div>
            </template>
        </el-table-column>
        <el-table-column
                label="操作"
                min-width="10%"
                align="center">
            <template slot-scope="scope">
                <div>删除</div>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
    import OrderCountBox from "@/components/OrderCountBox";
    export default {
        name: "Cart",
        components: {OrderCountBox},
        data() {
            return {
                tableData: [{
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "测试",
                    price: 10.00,
                    count: 2,
                }, {
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "测试",
                    price: 10.00,
                    count: 2,
                }, {
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "测试",
                    price: 10.00,
                    count: 2,
                }, {
                    choose: true,
                    img: require("@/static/1.jpg"),
                    title: "测试",
                    price: 10.00,
                    count: 2,
                }]
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