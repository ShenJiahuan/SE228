<template>
    <div>
        <el-tabs :value="active" @tab-click="handleClick">
            <el-tab-pane label="推荐" name="first" class="recommend-tab"></el-tab-pane>
            <el-tab-pane label="热榜" name="second" class="recommend-tab"></el-tab-pane>
        </el-tabs>
        <el-table
                :data="topList" :show-header="false">
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
    import Api from "@/components/Api.js";
    import { Loading } from "element-ui";

    export default {
        name: "Recommend",
        data() {
            return {
                bookList: bookList,
                topList: null,
            }
        },
        computed: {
            active() {
                if (this.$route.path === "/index/recommend") {
                    return "first";
                } else if (this.$route.path === "/index/hot") {
                    return "second";
                }
                return "";
            },
            path() {
                return this.$route.path;
            }
        },
        methods: {
            handleClick(tab) {
                if (tab.name === "first") {
                    this.$router.push("/index/recommend");
                } else {
                    this.$router.push("/index/hot");
                }
            },
            load() {
                let api = null;
                if (this.active === "first") {
                    api = Api.GetRecommendList;
                } else if (this.active === "second") {
                    api = Api.GetHotList;
                } else {
                    this.$router.push("/404");
                    return;
                }
                api(10).then(
                    response => {
                        let raw = response.data;
                        this.topList = [];
                        let cnt = 1;
                        for (let book of raw) {
                            let item = {
                                rank: cnt,
                                title: book.title,
                                img: this.$store.state.config.staticServer + "images/" + book.img,
                                id: book.bookId,
                            };
                            this.topList.push(item);
                            cnt += 1;
                        }
                    }, error => {
                        this.$notify({
                            title: "错误",
                            message: "未知错误",
                            type: "error"
                        });
                    }
                );
            }
        },
        mounted() {
            this.load();
        },
        watch: {
            path() {
                this.load();
            }
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
