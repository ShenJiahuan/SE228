<template>
    <el-form ref="form" :model="form" label-width="80px" class="form">
        <el-form-item label="昵称">
            <el-input placeholder="请输入昵称" v-model="form.username" class="entry"></el-input>
        </el-form-item>
        <el-form-item label="密码" show-password>
            <el-input placeholder="请输入密码" v-model="form.password" show-password class="entry"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">登录</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                form: {
                    username: '',
                    password: ''
                }
            }
        },
        methods: {
            onSubmit() {
                console.log('submit!');
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