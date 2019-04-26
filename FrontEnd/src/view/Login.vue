<template>
    <el-form ref="form" :model="form" label-width="80px" class="form" :rules="rules">
        <el-form-item label="邮箱" prop="email">
            <el-input placeholder="请输入邮箱" v-model="form.email" class="entry"></el-input>
        </el-form-item>
        <el-form-item label="密码" show-password>
            <el-input placeholder="请输入密码" v-model="form.password" show-password class="entry"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit('form')">登录</el-button>
            <span class="register" @click="handleRegister">没有账号？立即注册！</span>
        </el-form-item>
    </el-form>
</template>

<script>
    import Api from "@/components/Api.js";

    export default {
        name: "Login",
        data() {
            return {
                form: {
                    email: "",
                    password: ""
                },
                rules: {
                    email: [
                        { message: "请输入邮箱地址", trigger: "blur" },
                        { type: "email", message: "请输入正确的邮箱地址", trigger: "blur" }
                    ]
                }
            }
        },
        methods: {
            onSubmit(form) {
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        this.login(this.form.email, this.form.password);
                    } else {
                        return false;
                    }
                });
            },
            handleRegister() {
                this.$router.push({path: '/register', query: {redirect: this.$route.query.redirect}});
            },
            login(email, password) {
                Api.Login(email, password).then(
                    response => {
                        this.getUsername();
                        if (this.$route.query.redirect) {
                            this.$router.push(this.$route.query.redirect);
                        } else {
                            this.$router.push("/");
                        }
                    }, error => {
                        let message = "未知错误";
                        if (error.response.data.result === "Bad credentials") {
                            message = "邮箱或密码错误";
                        }
                        this.$notify({
                            title: "登录失败",
                            message: message,
                            type: "error"
                        });
                    }
                );
            },
            getUsername() {
                Api.GetUsername().then(
                    response => {
                        console.log(response);
                        let username = response.data.username;
                        let admin = response.data.admin === 1;
                        let root = response.data.root === 1;
                        this.$store.commit("login", {username:username, admin:admin, root:root});
                    }
                );
            }
        }
    }
</script>

<style scoped>
    .form {
        width: 400px;
        margin: 20px auto;
        text-align: left;
    }

    .entry {
        width: 300px;
    }

    .register {
        cursor: pointer;
        margin-left: 10px;
    }
</style>
