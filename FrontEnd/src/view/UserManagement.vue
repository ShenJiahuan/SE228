<template>
    <div>
        <el-table
                :data="tableData"
                style="width: 100%" v-if="tableData != null && tableData.length > 0">
            <el-table-column
                    prop="username"
                    label="用户名"
                    min-width="30%"
                    align="left">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    min-width="30%"
                    align="left">
            </el-table-column>
            <el-table-column
                    label="禁用"
                    min-width="20%"
                    align="center"
                    >
                <template slot-scope="scope">
                    <el-checkbox
                            v-model="scope.row.banned"
                            :disabled="(!$store.state.user.root && scope.row.admin) || ($store.state.user.username === scope.row.username)"
                            @change="handleBan(scope.$index)">
                    </el-checkbox>
                </template>
            </el-table-column>
            <el-table-column
                    label="管理员"
                    min-width="20%"
                    align="center">
                <template slot-scope="scope">
                    <el-checkbox
                            v-model="scope.row.admin"
                            :disabled="!$store.state.user.root || ($store.state.user.username === scope.row.username)"
                            @change="handleAdmin(scope.$index)">
                    </el-checkbox>
                </template>
            </el-table-column>
        </el-table>
        <el-card v-else-if="tableData != null">
            <h1>
                没有用户
            </h1>
        </el-card>
    </div>
</template>

<script>
    import Api from "@/components/Api.js";
    import { Loading } from "element-ui";

    export default {
        name: "UserManagement",
        data() {
            return {
                tableData: null,
            }
        },
        methods: {
            handleBan(row) {
                console.log(this.tableData[row]);
                Api.BanUser(this.tableData[row].id, this.tableData[row].banned).then(
                    response => {},
                    error => {
                        this.$notify.error({
                            title: "错误",
                            message: "无法禁用该用户"
                        });
                        this.tableData[row].banned = false;
                    }
                );
            },

            handleAdmin(row) {
                console.log(this.tableData[row]);
                Api.AdminUser(this.tableData[row].id, this.tableData[row].admin).then(
                    response => {},
                    error => {
                        this.$notify.error({
                            title: "错误",
                            message: "无法授予该用户管理员权限"
                        });
                        this.tableData[row].admin = false;
                    }
                );
            },
        },
        mounted() {
            Api.GetAllUser().then(
                response => {
                    let rawData = response.data;
                    this.tableData = [];
                    for (let user of rawData) {
                        this.tableData.push({
                            id: user.uid,
                            username: user.username,
                            email: user.email,
                            banned: user.banned === 1,
                            admin: user.admin === 1,
                            root: user.root === 1,
                        });
                    }
                }, error => {
                    this.$notify.error({
                        title: "错误",
                        message: "无法获取用户信息"
                    });
                }
            );
        }
    }
</script>

<style scoped>

</style>
