<template>
    <div>
        <h1 class="title">
            {{book.title}}
        </h1>
        <el-row class="top-content">
            <el-col :span="8">
                <div class="book-img">
                    <img :src="require('@/static/' + book.img)" />
                </div>
            </el-col>
            <el-col :span="8">
                <div class="book-info">
                    <span v-for="(value, key) in book.book_info" v-if="value !== null && key !== '定价'" :key="key">
                        <span class="key">{{key}}：</span>
                        <span class="value">{{value}}</span>
                        <br>
                    </span>
                    <span class="key">定价：</span>
                        <span class="value">
                        <span class="price">¥{{book.book_info['定价']}}</span>
                    </span>
                    <br>
                </div>
            </el-col>
            <el-col :span="8">
                <div class="score">
                    <h3>评分</h3>
                    <div>
                        <el-rate
                                :value="this.score(book)"
                                class="rate"
                                disabled
                                show-score
                                text-color="#ff9900"
                                score-template="{value}">
                        </el-rate>
                    </div>
                    <br>
                </div>
                <div class="book-order">
                    <el-form :inline="true" class="input-box">
                        <el-form-item label="数量">
                            <order-count-box
                                :num.sync='input'
                                size="medium"
                                min=1
                                max=999>
                            </order-count-box>
                        </el-form-item>
                    </el-form>
                    <el-button type="primary" class="button" @click.native="order">立即购买</el-button>
                    <el-button type="primary" class="button" @click.native="addCart">加入购物车</el-button>
                </div>
            </el-col>
        </el-row>
        <div class="book-desc">
            <div v-for="para in book.book_desc" :key="para">
                <p>{{para}}</p>
            </div>
        </div>
    </div>
</template>

<script>
    import bookList from "../data/book_list.json";
    import OrderCountBox from "@/components/OrderCountBox";
    export default {
        name: "BookInfo",
        components: {OrderCountBox},
        data() {
            return {
                input: 1,
                bookList: bookList,
                book: this.getBook(),
            }
        },
        methods: {
            getBook() {
                let id = parseInt(this.$route.params.id);
                for (let book of bookList) {
                    if (book.id === id) {
                        return book;
                    }
                }
            },
            order() {
                if (this.$store.state.user.username === null) {
                    this.$notify.error({
                        title: "错误",
                        message: "请先登录"
                    });
                    this.$router.push({path: '/login', query: {redirect: this.$route.fullPath}});
                } else {
                    this.$notify({
                        title: "成功",
                        message: "购买成功",
                        type: "success"
                    });
                }
            },
            addCart() {
                if (this.$store.state.user.username === null) {
                    this.$notify.error({
                        title: "错误",
                        message: "请先登录"
                    });
                    this.$router.push({path: '/login', query: {redirect: this.$route.fullPath}});
                } else {
                    this.$notify({
                        title: "成功",
                        message: "加入购物车成功",
                        type: "success"
                    });
                }
            },
            score(book) {
                return parseFloat((book.score / 2).toFixed(1));
            }
        },
        updated() {
            this.book = this.getBook();
        }
    }
</script>

<style scoped>
    .title, .top-content {
        border-bottom: 1px #E4E7ED solid;
    }

    h1 {
        padding-bottom: 20px;
    }

    .book-img {
        height: 300px;
    }

    .book-img img {
        height: 100%;
        border-radius: 2px;
    }

    .book-info {
        text-align: left;
        line-height: 1.5;
    }

    .book-order {
        padding-top: 20px;
    }

    .input-box {
        margin-bottom: 20px;
    }

    .button {
        width: 120px;
    }

    .top-content {
        padding-bottom: 20px;
    }

    .book-desc {
        text-align: left;
        padding: 40px;
        line-height: 1.5 !important;
    }

    .price {
        color: #ff0036;
        font-size: 20px;
    }

    .score {
        text-align: left;
        padding-left: 40px;
    }
</style>
