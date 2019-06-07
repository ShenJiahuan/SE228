<template>
    <div v-if="book != null">
        <h1 class="title">
            <div class="title-name">{{book.snapshot.title}}</div>
            <div v-if="this.$store.state.user.admin">
                <router-link v-bind:to="'/book/edit/' + book.snapshot.bookId">
                    <el-button class="title-edit" type="primary" icon="el-icon-edit" circle></el-button>
                </router-link>
                <el-button class="title-edit" type="danger" icon="el-icon-delete" circle v-if="this.$store.state.user.admin" @click="deleteBook"></el-button>
            </div>
            </h1>

        <el-row class="top-content">
            <el-col :span="8">
                <div class="book-img">
                    <img :src="img_src" />
                </div>
            </el-col>
            <el-col :span="8">
                <div class="book-info">
                    <span>
                        <span>作者：</span>
                        <span>{{book.snapshot.author}}</span>
                    </span>
                    <br>
                    <span>
                        <span>出版社：</span>
                        <span>{{book.snapshot.publisher}}</span>
                    </span>
                    <br>
                    <span>
                        <span>出版年：</span>
                        <span>{{book.snapshot.publishDate}}</span>
                    </span>
                    <br>
                    <span>
                        <span>页数：</span>
                        <span>{{book.snapshot.pages}}</span>
                    </span>
                    <br>
                    <span>
                        <span>装帧：</span>
                        <span>{{book.snapshot.decoration}}</span>
                    </span>
                    <br>
                    <span>
                        <span>ISBN：</span>
                        <span>{{book.snapshot.isbn}}</span>
                    </span>
                    <br>
                    <span class="key">定价：</span>
                    <span class="value">
                        <span class="price">¥{{book.snapshot.price}}</span>
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
                    <div class="book-remain">
                        剩余&nbsp{{book.snapshot.remain}}件
                    </div>
                    <el-button type="primary" class="button" @click.native="order(true)">立即购买</el-button>
                    <el-button type="primary" class="button" @click.native="order(false)">加入购物车</el-button>
                </div>
            </el-col>
        </el-row>
        <div class="book-desc">
            <div v-if="book.snapshot.bookDesc != null">
                <div v-for="(para, index) in book.snapshot.bookDesc.split('\n')" :key="index">
                    <p>{{para}}</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import OrderCountBox from "@/components/OrderCountBox";
    import Api from "@/components/Api.js";
    import { Loading } from "element-ui";

    export default {
        name: "BookInfo",
        components: {OrderCountBox},
        data() {
            return {
                input: 1,
                book: null,
                img_src: null,
            }
        },
        computed: {
            bookID() {
                return this.$route.params.id;
            }
        },
        methods: {
            order(paid) {
                if (this.$store.state.user.username == null || this.$store.state.user.username === "") {
                    this.$notify.error({
                        title: "错误",
                        message: "请先登录"
                    });
                    this.$router.push({path: '/login', query: {redirect: this.$route.fullPath}});
                } else {
                    let api = null;
                    let data = null;
                    if (paid) {
                        api = Api.CreateOrder;
                        data = {
                            orders: [
                                {
                                    id: parseInt(this.bookID),
                                    count: this.input,
                                }
                            ]
                        };
                    } else {
                        api = Api.CreateCart;
                        data = {
                            cartItem: {
                                id: parseInt(this.bookID),
                                count: this.input,
                            }
                        };
                    }
                    api(data).then(
                        response => {
                            let message = paid ? "购买成功" : "加入购物车成功";
                            this.$notify({
                                title: "成功",
                                message: message,
                                type: "success"
                            });
                            if (paid) {
                                this.book.snapshot.remain -= this.input;
                            }
                        }, error => {
                            switch (error.response.data.status) {
                                case 400:
                                    let message = error.response.data.message === "books not enough" ? "库存不足" : "未知错误";
                                    this.$notify({
                                        title: "错误",
                                        message: message,
                                        type: "error"
                                    });
                                    break;
                                case 403:
                                    this.$notify({
                                        title: "错误",
                                        message: "您的账户已被禁用，请联系管理员",
                                        type: "error"
                                    });
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
            score(book) {
                return parseFloat((book.snapshot.score / 2).toFixed(1));
            },
            getBookInfo(bookid) {
                Api.GetBookInfo(bookid)
                    .then(response => {
                        this.book = response.data;
                    }, error => {
                        switch (error.response.data.status) {
                            case 404:
                                this.$notify({
                                    title: "错误",
                                    message: "书籍不存在",
                                    type: "error"
                                });
                                break;
                            default:
                                this.$notify({
                                    title: "错误",
                                    message: "未知错误",
                                    type: "error"
                                });
                                break;
                        }
                        this.$router.push("/");
                    });

                Api.GetBookImage(bookid)
                    .then(response => {
                        this.img_src = response.data;
                    })
            },
            deleteBook() {
                Api.DeleteBook(this.bookID)
                    .then(response => {
                        this.$notify.success({
                            title: "成功",
                            message: "删除书籍成功"
                        });
                        this.$router.push("/");
                    }, error=> {
                        switch (error.response.data.status) {
                            case 404:
                                this.$notify({
                                    title: "错误",
                                    message: "书籍不存在",
                                    type: "error"
                                });
                                break;
                            default:
                                this.$notify({
                                    title: "错误",
                                    message: "未知错误",
                                    type: "error"
                                });
                                break;
                        }
                    });
            }
        },
        created() {
            this.getBookInfo(this.bookID);
        },
        watch: {
            bookID() {
                this.getBookInfo(this.bookID);
            }
        }
    }
</script>

<style scoped>
    .title, .top-content {
        border-bottom: 1px #E4E7ED solid;
    }

    .title {
        display: flex;
        text-align: center;
        justify-content: center;
        align-items: center;
    }

    .title-edit {
        height: 40px;
        width: 40px;
        margin: 0 5px;
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

    .book-remain {
        margin-bottom: 20px;
        text-align: left;
        padding-left: 40px;
        font-size: 14px;
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
