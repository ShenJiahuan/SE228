<template>
    <div>
        <el-tabs v-model="active" @tab-click="handleClick">
            <el-tab-pane label="推荐" name="first" class="recommend-tab"></el-tab-pane>
            <el-tab-pane label="热榜" name="second" class="recommend-tab"></el-tab-pane>
        </el-tabs>
        <el-table
                :data="tableData" :show-header="false">
            <el-table-column
                    min-width="10%">
                <template slot-scope="scope">
                    <div :class="{'index-top': scope.row.rank <= 3}">{{scope.row.rank}}</div>
                </template>
            </el-table-column>
            <el-table-column
                    min-width="75%">
                <template slot-scope="scope">
                    <router-link :to="'/info/' + scope.row.id">{{scope.row.title}}</router-link>
                </template>
            </el-table-column>
            <el-table-column
                    min-width="15%"
                    align="center">
                <template slot-scope="scope">
                    <img :src="scope.row.img" class="recommend-img"/>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    import bookList from "../data/book_list.json";
    export default {
        name: "Recommend",
        data() {
            return {
                active: "first",
                bookList: bookList,
            }
        },
        computed: {
            tableData() {
                var data = [];
                for (let book of bookList) {
                    var item = {
                        rank: book.id,
                        title: book.title,
                        img: require("@/static/" + book.img),
                        id: book.id,
                    };
                    data.push(item);
                    if (data.length >= 10) {
                        break;
                    }
                }
                if (this.active === "second") {
                    data = data.sort((book1, book2) => book1.title < book2.title ? -1 : 1);
                    let cnt = 1;
                    data = data.map(book => {
                        book.rank = cnt;
                        cnt++;
                        return book;
                    });
                }
                return data;
            }
        },
        methods: {
            handleClick() {
                if (this.active === "first") {
                    this.$router.push("/index/recommend");
                } else {
                    this.$router.push("/index/hot");
                }
            },
            getActive() {
                if (this.$route.params.choice === "recommend") {
                    this.active = "first";
                } else if (this.$route.params.choice === "hot") {
                    this.active = "second";
                }
            }
        },
        mounted() {
            this.getActive();
        },
        updated() {
            this.getActive();
        }
    }
</script>

<style>
    .el-tabs__item {
        font-size: 18px !important;
    }

    .cell {
        font-size: 18px;
        font-weight: bold;
    }

    .index-top {
        color: #FF9607 !important;
    }

    .recommend-img {
        height: 120px;
        text-align: center;
        border-radius: 2px;
    }
</style>