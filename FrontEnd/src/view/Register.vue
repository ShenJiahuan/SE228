<template>
    <el-form ref="form" :model="form" label-width="80px" class="form" :rules="rules">
        <el-form-item label="昵称">
            <el-input placeholder="请输入昵称" v-model="form.username" class="entry"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
            <el-input placeholder="请输入邮箱" v-model="form.email" class="entry"></el-input>
        </el-form-item>
        <el-form-item label="密码" show-password prop="password">
            <el-input placeholder="请输入密码" v-model="form.password" show-password class="entry"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" show-password prop="checkPass">
            <el-input placeholder="请确认密码" v-model="form.checkPass" show-password class="entry"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit('form')">注册</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        name: "Register",
        data() {
            var validatePass = (rule, value, callback) => {
                var pattern1 = /[0-9]/;
                var pattern2 = /[A-Za-z]/;
                if (value === "") {
                    callback(new Error("请输入密码"));
                } else if (value.length < 8 || !pattern1.test(value) || !pattern2.test(value)) {
                    callback(new Error("密码过于简单"));
                } else {
                    if (this.form.checkPass !== "") {
                        this.$refs.form.validateField("checkPass");
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === "") {
                    callback(new Error("请再次输入密码"));
                } else if (value !== this.form.password) {
                    callback(new Error("两次输入密码不一致!"));
                } else {
                    callback();
                }
            };
            return {
                form: {
                    username: "",
                    email: "",
                    password: "",
                    checkPass: "",
                },
                rules: {
                    email: [
                        { message: "请输入邮箱地址", trigger: "blur" },
                        { type: "email", message: "请输入正确的邮箱地址", trigger: "blur" }
                    ],
                    password: [
                        { validator: validatePass, trigger: "blur" }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: "blur" }
                    ],
                }
            }
        },
        methods: {
            onSubmit(form) {
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        this.$store.commit("login", this.form.username, this.form.email, false);
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