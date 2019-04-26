<template>
    <div v-if="bookList != null">
        <search-bar></search-bar>
        <div v-if="bookList.length > 0">
            <el-tabs v-model="active" @tab-click="handleClick">
                <el-tab-pane :label="firstLabel" name="first"></el-tab-pane>
                <el-tab-pane :label="secondLabel" name="second"></el-tab-pane>
                <el-tab-pane :label="thirdLabel" name="third"></el-tab-pane>
            </el-tabs>
            <transition-group class="list-group" name="flip-list" tag="div">
                <list-item v-for="book in bookList" :book="book" :key="book.bookId"></list-item>
            </transition-group>
        </div>
        <el-card v-else>
            <h2>
                未找到图书"{{this.$route.params.keyword}}"
            </h2>
        </el-card>
    </div>
</template>

<script>
    import ListItem from "@/components/ListItem";
    import SearchBar from "@/components/SearchBar";
    import Api from "@/components/Api.js";
    import { Loading } from "element-ui";

    export default {
        name: "BookList",
        components: {SearchBar, ListItem},
        data() {
            return {
                active: "first",
                firstLabel: "综合",
                secondLabel: "价格",
                thirdLabel: "销量",
                bookList: null,
                /* sortType:
                 * 0: sort by complex, 1: sort by price ascent, 2: sort by price descent, 3: sort by sale amount
                 */
                sortType: 0,
            }
        },
        computed: {
            path() {
                return this.$route.path;
            },
            keyword() {
                return this.$route.params.keyword;
            }
        },
        methods: {
            handleClick(tab) {
                switch (tab.label) {
                    case "价格":
                    case "价格由高到低":
                        this.secondLabel = "价格由低到高";
                        this.sortType = 1;
                        break;
                    case "价格由低到高":
                        this.secondLabel = "价格由高到低";
                        this.sortType = 2;
                        break;
                    case "综合":
                        this.secondLabel = "价格";
                        this.sortType = 0;
                        break;
                    case "销量":
                        this.secondLabel = "价格";
                        this.sortType = 3;
                        break;
                }
                this.sort();
            },
            sort() {
                switch (this.sortType) {
                    case 0:
                    case 3:
                        this.bookList = this.bookList.sort((book1, book2) => book1.bookId < book2.bookId ? -1 : 1);
                        break;
                    case 1:
                        this.bookList = this.bookList.sort((book1, book2) => parseFloat(book1.price) < parseFloat(book2.price) ? -1 : 1);
                        break;
                    case 2:
                        this.bookList = this.bookList.sort((book1, book2) => parseFloat(book1.price) > parseFloat(book2.price) ? -1 : 1);
                        break;
                }
            },
            getBookList(keyword) {
                this.bookList = null;
                Api.GetBookList(keyword).then(
                    response => {
                        this.bookList = response.data;
                    },
                    error => {
                        switch (error.response.data.status) {
                            case 404:
                                this.bookList = [];
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
            this.getBookList(this.keyword);
        },
        watch: {
            keyword() {
                this.getBookList(this.keyword);
            }
        }
    }
</script>

<style scoped>
    .list-group {
        display: flex;
        flex-flow: row wrap;
    }

    .flip-list-move {
        transition: transform 0.5s;
    }
</style>
