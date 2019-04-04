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
                <list-item v-for="book in bookList" :book="book" :key="book.id"></list-item>
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
                loadingInstance: null,
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
                if (tab.label === "价格" || tab.label === "价格由高到低") {
                    this.secondLabel = "价格由低到高";
                    this.bookList = this.bookList.sort((book1, book2) => parseFloat(book1.book_info["定价"]) < parseFloat(book2.book_info["定价"]) ? -1 : 1);
                } else if (tab.label === "价格由低到高") {
                    this.secondLabel = "价格由高到低";
                    this.bookList = this.bookList.sort((book1, book2) => parseFloat(book1.book_info["定价"]) > parseFloat(book2.book_info["定价"]) ? -1 : 1);
                } else if (tab.label === "综合") {
                    this.secondLabel = "价格";
                    this.bookList = this.bookList.sort((book1, book2) => book1.id < book2.id ? -1 : 1);
                } else if (tab.label === "销量") {
                    this.secondLabel = "价格";
                    this.bookList = this.bookList.sort((book1, book2) => book1.id < book2.id ? -1 : 1);
                }
            }
        },
        created() {
            this.loadingInstance = Loading.service({ fullscreen: true });
            Api.GetBookList({keyword: this.keyword}).then(
                response => {
                    this.bookList = response.data;
                    this.loadingInstance.close();
                }
            );
        },
        watch: {
            keyword() {
                this.loadingInstance = Loading.service({ fullscreen: true });
                Api.GetBookList({keyword: this.keyword}).then(
                    response => {
                        this.bookList = response.data;
                        this.loadingInstance.close();
                    }
                );
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
