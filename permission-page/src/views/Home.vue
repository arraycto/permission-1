<template>
    <el-container class="main-container">
        <el-header class="el-header" style="color: #eaeaea">
            <h2 style="font-size: 30px">{{title}}</h2>
            <span style="font-size:16px">{{oneWord}}</span>
            <el-dropdown @command="handleCommand" style="color: #eaeaea">
                <span><i class="el-icon-arrow-down" style="margin-right: 5px"></i>{{admin.nickname}}</span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="main">主页</el-dropdown-item>
                    <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </el-header>
        <el-container class="bottom-container">
            <el-aside width="220px" style="height:100%;">
                <el-menu class="el-menu"
                         background-color="#2c3e50"
                         text-color="#FFF"
                         :default-active="activeMenu"
                         active-text-color="#009688" router unique-opened :collapse="false">
                    <el-submenu :index="menu.path" class="el-submenu"
                                v-for="(menu,index) in routes" :key="index"
                                v-if="!menu.hidden&&menu.children.length!==1">
                        <template slot="title">
                            <i :class="menu.icon"></i>
                            <span slot="title">{{menu.name}}</span>
                        </template>
                        <el-menu-item v-if="!item.hidden" :index="item.path" v-for="item in menu.children" :key="item.id">
                            <i :class="item.icon"></i>
                            <span slot="title">{{item.name}}</span>
                        </el-menu-item>
                    </el-submenu>
                    <!--单级菜单-->
                    <el-menu-item v-else-if="menu.children&&menu.children.length===1" :index="menu.children[0].path">
                        <i :class="menu.icon"/>
                        <span slot="title">{{menu.name}}</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main class="main-pane">
                <el-card size="mini" class="box-card">
                    <el-breadcrumb slot="header" separator-class="el-icon-arrow-right"
                                   v-if="this.$router.currentRoute.path!=='/'">
                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <router-view/>
                </el-card>
            </el-main>
        </el-container>
    </el-container>
</template>
<script>
    import admin from '../api/admin'
    import {mapGetters} from 'vuex'

    export default {
        name: "Home",
        data() {
            return {
                title: process.env.VUE_APP_TITLE,
                oneWord: process.env.VUE_APP_ONE_WORD
        }
        },
        // ...
        computed: {
            ...mapGetters([
                'admin'
            ]),
            routes() {
                return this.$store.getters.routes
            },
            activeMenu() {
                return this.$route.path
            }
        },
        methods: {
            logout() {
                this.$confirm('确定注销登陆吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    admin.logout();
                    window.sessionStorage.removeItem("admin");
                    this.$store.commit("setAdmin", {});
                    this.$store.commit("setRoutes", []);
                    this.$store.commit("setRemoteRoutes", []);
                    this.$router.replace("/login");
                    this.$message.success("注销成功");
                }).catch(() => {
                    this.$message.warning("操作已取消");
                });
            },
            handleCommand(command) {
                switch (command) {
                    case 'main':
                        this.$router.replace("/");
                        break;
                    case 'logout':
                        this.logout();
                        break;
                }
            }
        },
        created() {
        }
    }
</script>

<style>
    * {
        padding: 0;
        margin: 0;
    }

    .main-container {
        min-height: 100%;
        height: 100%;
    }

    .el-header {
        background-color: #2c3e50;
        color: #333;
        line-height: 60px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        text-align: right;
        font-size: 12px;
    }

    .bottom-container {
        height: calc(100% - 60px);
        min-height: calc(100% - 60px);
    }

    .el-menu {
        min-height: 100%;
    }

    .main-pane {
        padding: 5px !important;
        height: 100%;
    }

    .box-card {
        height: 99%;
        max-height: 99%;
        overflow: auto;
    }

    div.el-card__header {
        padding: 10px 20px;
        border-bottom: 1px solid #EBEEF5;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
    }
</style>
