<template>
    <el-form ref="form" :model="form" label-width="80px" class="form">
        <div class="form-data">
            <div class="form-left">
                <el-form-item label="标题" prop="title" required>
                    <el-input v-model="form.title" class="entry"></el-input>
                </el-form-item>
                <el-form-item label="作者" prop="author">
                    <el-input v-model="form.author" class="entry"></el-input>
                </el-form-item>
                <el-form-item label="出版社" prop="publisher">
                    <el-input v-model="form.publisher" class="entry"></el-input>
                </el-form-item>
                <el-form-item label="出版日期" prop="publishDate">
                    <el-input v-model="form.publishDate" class="entry"></el-input>
                </el-form-item>
                <el-form-item label="页数" prop="pages">
                    <el-input v-model="form.pages" class="entry"></el-input>
                </el-form-item>
                <el-form-item label="定价" prop="price" required>
                    <el-input v-model="form.price" class="entry"></el-input>
                </el-form-item>
                <el-form-item label="装帧" prop="decoration">
                    <el-input v-model="form.decoration" class="entry"></el-input>
                </el-form-item>
            </div>
            <div class="form-right">
                <el-form-item label="ISBN" prop="isbn" required>
                    <el-input v-model="form.isbn" class="entry"></el-input>
                </el-form-item>
                <el-form-item label="评分" prop="score">
                    <el-input v-model="form.score" class="entry"></el-input>
                </el-form-item>
                <el-form-item label="详情" prop="bookDesc">
                    <el-input type="textarea" v-model="form.bookDesc" class="entry"></el-input>
                </el-form-item>
                <el-form-item label="库存" prop="remain" required>
                    <el-input v-model="form.remain" class="entry"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-upload
                            class="image-uploader"
                            :action="this.$store.state.config.backendServer + 'upload/image'"
                            :show-file-list="false"
                            :with-credentials="true"
                            :on-success="handleSuccess"
                            :on-error="handleError"
                            :before-upload="beforeUpload">
                        <img v-if="localImg" :src="localImg" class="image">
                        <i v-else class="el-icon-plus image-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
            </div>
        </div>

        <el-form-item>
            <el-button type="primary" @click="onSubmit">更新</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import Api from "@/components/Api.js";

    export default {
        name: "UpdateBook",
        data() {
            return {
                form: {
                    bookId: null,
                    title: null,
                    author: null,
                    publisher: null,
                    publishDate: null,
                    pages: null,
                    price: null,
                    decoration: null,
                    isbn: null,
                    score: null,
                    bookDesc: null,
                    remain: null,
                    imgFileName: "",
                },
                localImg: "",
            }
        },
        computed: {
            username() {
                return this.$store.state.user.username;
            },
            bookId() {
                return this.$route.params.id;
            }
        },
        methods: {
            handleSuccess(res, file) {
                console.log(res);
                this.form.imgFileName = res;
                this.localImg = URL.createObjectURL(file.raw);
                this.$notify.success({
                    title: "成功",
                    message: "上传图片成功"
                });
            },
            handleError(err, file) {
                this.$notify.error({
                    title: "错误",
                    message: "无法上传图片"
                });
            },
            beforeUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                if (!isJPG) {
                    this.$notify.error({
                        title: "错误",
                        message: "上传图片只能是 JPG 格式！"
                    });
                }
                return isJPG;
            },
            onSubmit() {
                Api.UpdateBook(this.form).then(
                    response => {
                        this.$notify({
                            title: "成功",
                            message: "更新书籍成功",
                            type: "success"
                        });
                        this.$router.push("/");
                    }, error => {
                        switch (error.response.data.status) {
                            case 400:
                                this.$notify({
                                    title: "错误",
                                    message: "参数错误",
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
            },
            getbook() {
                if (this.username == null) {
                    return;
                }
                if (!this.$store.state.user.admin) {
                    this.$notify.error({
                        title: "错误",
                        message: "您无权限访问该页面"
                    });
                    this.$router.push("/");
                }

                Api.GetBookInfo(this.bookId)
                    .then(response => {
                        console.log(response);
                        this.form = {
                            bookId: this.bookId,
                            title: response.data.snapshot.title,
                            author: response.data.snapshot.author,
                            publisher: response.data.snapshot.publisher,
                            publishDate: response.data.snapshot.publishDate,
                            pages: response.data.snapshot.pages,
                            price: response.data.snapshot.price,
                            decoration: response.data.snapshot.decoration,
                            isbn: response.data.snapshot.isbn,
                            score: response.data.snapshot.score,
                            bookDesc: response.data.snapshot.bookDesc,
                            remain: response.data.snapshot.remain,
                        };
                        Api.GetBookImage(this.bookId).then(
                            response => {
                                this.localImg = response.data;
                            }
                        );
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
            }
        },
        watch: {
            username() {
                this.getbook();
            },
            bookId() {
                this.getbook();
            }
        },
        mounted() {
            this.getbook();
        },
    }
</script>

<style scoped>
    .form-data {
        display: flex;
    }

    .form-left, .form-right {
        width: 50%;
    }

    .image-uploader-icon {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .image-uploader-icon:hover {
        border-color: #409EFF;
    }

    .image-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 160px;
        height: 240px;
        line-height: 178px;
        text-align: center;
    }
    .image {
        height: 240px;
        text-align: center;
        border-radius: 2px;
        display: block;
    }
</style>
