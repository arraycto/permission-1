<template>
    <div class="bg">
        <el-form
                :rules="rules"
                ref="loginForm"
                v-loading="loading"
                element-loading-text="正在登录..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                :model="loginForm"
                class="loginContainer">
            <h3 class="loginTitle">系统登录</h3>
            <el-form-item prop="username">
                <el-input size="normal" type="text" v-model="loginForm.username" auto-complete="off"
                          placeholder="请输入用户名">
                    <i slot="prefix" class="el-icon-user-solid"></i>
                </el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input size="normal" type="password" v-model="loginForm.password" auto-complete="off"
                          placeholder="请输入密码">
                    <i slot="prefix" class="el-icon-lock"></i>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-input class="code-input" size="normal" type="text" v-model="loginForm.code" auto-complete="off"
                          placeholder="请输入计算结果" @keydown.enter.native="submitLogin">
                    <i slot="prefix" class="el-icon-key"></i>
                </el-input>
                <div class="code-image">
                    <el-image fit="fill" src="http://localhost:8888/code"></el-image>
                </div>
            </el-form-item>
            <el-checkbox size="normal" class="loginRemember" v-model="rememberMe">记住我</el-checkbox>
            <el-button size="normal" type="primary" style="width: 100%;" @click="submitLogin">登录</el-button>
        </el-form>
    </div>
</template>

<script>
    import api from "../api/admin";

    export default {
        name: "Login",
        data() {
            return {
                loading: false,
                loginForm: {
                    username: 'admin',
                    password: 'uncle',
                    code: ''
                },
                rememberMe: true,
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}]
                }
            }
        },
        methods: {
            submitLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        api.login(this.loginForm).then(res => {
                            console.log(res.data)
                            this.$router.replace("/home")
                        })
                    } else {
                        this.$message.error('请输入所有字段');
                        return false;
                    }
                });
            }
        }
    }
</script>

<style>
    * {
        margin: 0;
        padding: 0;
    }

    .bg {
        background-image: url("~@/assets/bg.jpg");
        position: absolute;
        background-size: cover;
        width: 100%;
        height: 100%;
        /* 背景图垂直、水平均居中 */
        background-position: center center;
        /* 当内容高度大于图片高度时，背景图像的位置相对于viewport固定 */
        background-attachment: fixed;
    }

    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 200px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .loginTitle {
        margin: 15px auto 20px auto;
        text-align: center;
        color: #505458;
    }

    .loginRemember {
        text-align: left;
        margin: 0 0 15px 0;
        float: left;
    }

    .code-image {
        width: 30%;
        display: inline-block;
        height: 40px;
        float: right;
    }

    .code-image img {
        height: 40px;
        cursor: pointer;
        vertical-align: middle;
    }

    .code-input {
        max-width: 70%;
    }
</style>