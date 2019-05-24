<template>
    <div>
        <el-table
                :data="pageTableData"
                style="width: 100%">
            <el-table-column
                    label="图片"
                    min-width="15%"
                    align="center">
                <template slot-scope="scope">
                    <img :src="scope.row.img" class="manage-img"/>
                </template>
            </el-table-column>
            <el-table-column
                    label="标题"
                    min-width="18%"
                    align="left">
                <template slot-scope="scope">
                    <router-link :to="'/info/' + scope.row.id">{{scope.row.title}}</router-link>
                </template>
            </el-table-column>
            <el-table-column
                    prop="author"
                    label="作者"
                    min-width="22%"
                    align="left">
            </el-table-column>
            <el-table-column
                    prop="isbn"
                    label="ISBN"
                    min-width="20%"
                    align="left">
            </el-table-column>
            <el-table-column
                    prop="remain"
                    label="库存"
                    min-width="10%"
                    align="left">
            </el-table-column>
            <el-table-column
                    label="操作"
                    min-width="15%"
                    align="center">
                <template slot-scope="scope" class="button-group">
                    <el-button type="primary" icon="el-icon-edit" circle  v-on:click="editBook(scope.row.id)"></el-button>
                    <el-button type="danger" icon="el-icon-delete" circle  v-on:click="deleteBook(scope.row.id)"></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                layout="prev, pager, next"
                :total="count"
                :current-page="currentPage"
                @current-change="handleCurrentChange">
        </el-pagination>
    </div>

</template>

<script>
    import Api from "@/components/Api.js";

    export default {
        name: "BookManagement",
        data() {
            return {
                tableData: null,
                currentPage: 1,
                pageSize: 10,
            }
        },
        computed: {
            count() {
                if (this.tableData == null) {
                    return 0;
                } else {
                    return this.tableData.length;
                }
            },
            pageTableData() {
                let result = [];
                let begin = (this.currentPage - 1) * this.pageSize;
                let end = Math.min(begin + this.pageSize, this.count);
                for (let i = begin; i < end; i++) {
                    result.push(this.tableData[i]);
                }
                return result;
            }
        },
        methods: {
            handleCurrentChange(val) {
                this.currentPage = val;
            },
            editBook(id) {
                this.$router.push('/book/edit/' + id);
            },
            deleteBook(id) {
                Api.DeleteBook(id).then(
                    response => {
                        this.$notify.success({
                            title: "成功",
                            message: "删除书籍成功"
                        });
                        for (let i = 0; i < this.count; i++) {
                            if (this.tableData[i].id === id) {
                                this.tableData.splice(i, 1);
                                break;
                            }
                        }
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
                    }
                );
            }
        },
        mounted() {
            Api.GetAllBooks().then(
                response => {
                    let rawData = response.data;
                    this.tableData = [];
                    for (let book of rawData) {
                        this.tableData.push({
                            id: book.snapshot.bookId,
                            title: book.snapshot.title,
                            author: book.snapshot.author,
                            isbn: book.snapshot.isbn,
                            remain: book.snapshot.remain,
                            img: this.$store.state.config.staticServer + 'images/' + book.snapshot.img,
                        });
                    }
                }, error => {
                    switch (error.response.data.status) {
                        case 401:
                            this.$notify.error({
                                title: "错误",
                                message: "请先登录"
                            });
                            this.$router.push({path: '/login', query: {redirect: this.$route.fullPath}});
                            break;
                        case 403:
                            this.$notify.error({
                                title: "错误",
                                message: "您无权限访问该页面"
                            });
                            this.$router.push("/");
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
    }
</script>

<style scoped>
    .manage-img {
        height: 120px;
        text-align: center;
        border-radius: 2px;
    }

    .button-group {
        display: flex;
    }
</style>
