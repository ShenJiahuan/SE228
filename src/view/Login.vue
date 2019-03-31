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
        </el-form-item>
    </el-form>
</template>

<script>
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
                        this.$store.commit("login", "未知用户", this.form.email, false);
                        if (this.$route.query.redirect) {
                            this.$router.push(this.$route.query.redirect);
                        } else {
                            this.$router.push("/");
                        }
                    } else {
                        return false;
                    }
                });
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
</style>