<template>
    <div>
        <el-form
                :rules="rules"
                ref="loginForm"
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
            <el-form-item prop="code">
                <el-input class="code-input" size="normal" type="text" v-model="loginForm.code" auto-complete="off"
                          placeholder="请输入计算结果" @keydown.enter.native="submitLogin">
                    <i slot="prefix" class="el-icon-key"></i>
                </el-input>
                <div class="code-image">
                    <el-image fit="fill" :src="codeUri" @click="refreshCode()"></el-image>
                </div>
            </el-form-item>
            <el-checkbox size="normal" class="loginRemember" v-model="rememberMe">记住我</el-checkbox>
            <el-button size="normal" type="primary" style="width: 100%;" @click="submitLogin" :loading="loading">
                <span v-if="!loading">登 录</span>
                <span v-else>正 在 登 录...</span>
            </el-button>
        </el-form>
        <vue-particles
                class="bg"
                color="#dedede"
                :particleOpacity="0.7"
                :particlesNumber="80"
                shapeType="star"
                :particleSize="4"
                linesColor="#888888"
                :linesWidth="2"
                :lineLinked="true"
                :lineOpacity="0.4"
                :linesDistance="150"
                :moveSpeed="3"
                :hoverEffect="true"
                hoverMode="grab"
                :clickEffect="true"
                clickMode="push"/>
    </div>
</template>

<script>
    import api from "../api/admin";
    import encrypt from "../utils/encrypt";
    import cookie from "js-cookie"

    export default {
        name: "Login",
        data() {
            return {
                loading: false,
                loginForm: {
                    username: '',
                    password: '',
                    code: '',
                },
                codeUrl: process.env.VUE_APP_BASE_API + 'auth/code?',
                date: Date.now(),
                rememberMe: true,
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                    code: [{required: true, message: '验证码不能为空', trigger: 'blur'}],
                }
            }
        },
        computed: {
            codeUri: function () {
                return this.codeUrl + this.date;
            }
        },
        created() {
            this.loadCookiesUser()
        },
        methods: {
            loadCookiesUser() {
                let userInfo = cookie.get("admin");
                if (userInfo) {
                    userInfo = JSON.parse(userInfo);
                    this.$set(this.loginForm, "username", userInfo.username);
                    this.$set(this.loginForm, "password", userInfo.password);
                    this.rememberMe = userInfo.rememberMe;
                }
            },
            submitLogin() {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        let user = {...this.loginForm};
                        if (user.password.length < 50) {
                            user.password = encrypt(user.password);
                        }
                        api.login(user).then(res => {
                            this.loading = false;
                            if (res.success) {
                                //记住账号密码
                                if (this.rememberMe) {
                                    cookie.set("admin", JSON.stringify({
                                        rememberMe: this.rememberMe,
                                        username: user.username,
                                        password: user.password
                                    }))
                                }
                                let adminInfo = {rememberMe: this.rememberMe, ...res.data};
                                window.sessionStorage.setItem("admin", JSON.stringify(adminInfo));
                                this.$store.commit("setAdmin", adminInfo);
                                this.$message.success(res.msg);
                                let path = this.$route.query.from;
                                this.$router.replace(path ? path : "/home")
                            } else {
                                this.refreshCode();
                            }
                        }).catch(e => {
                            this.refreshCode()
                        })
                    }
                });
            },
            refreshCode() {
                this.date = Date.now();
            },
        }
    }
</script>

<style>
    * {
        margin: 0;
        padding: 0;
    }

    .main {
        z-index: 1;
    }

    .bg-img {
        /*background-image: url("~@/assets/bg.jpg");*/
        background-color: #2c3e50;
        position: absolute;
        background-size: cover;
        width: 100%;
        height: 100%;
        /* 背景图垂直、水平均居中 */
        background-position: center center;
        /* 当内容高度大于图片高度时，背景图像的位置相对于viewport固定 */
        background-attachment: fixed;
    }

    .bg {
        position: absolute;
        background-size: cover;
        background-color: #2c3e50;
        height: 100%;
        width: 100%;
        top: 0;
        z-index: 0;
        /* 背景图垂直、水平均居中 */
        background-position: center center;
        /* 当内容高度大于图片高度时，背景图像的位置相对于viewport固定 */
        background-attachment: fixed;
    }

    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        top: calc((100% - 380px) / 2);
        height: 380px;
        width: 400px;
        padding: 15px 35px 15px 35px;
        box-sizing: border-box;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
        position: absolute;
        left: calc((100% - 400px) / 2);
        z-index: 1;
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
