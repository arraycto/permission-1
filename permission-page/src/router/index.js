import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from "../views/Home";

Vue.use(VueRouter)

export const localRoutes = [
    {
        path: '/login',
        name: 'login',
        component: Login,
        hidden: true
    },
    {
        path: '/',
        name: '主页',
        icon: 'el-icon-s-home',
        hidden: true,
        component: Home,
        children: [
            {
                path: '/home',
                component: () => import("../views/local/userCenter"),
            }
        ]
    },

];

const router = new VueRouter({
    routes: localRoutes
});

export default router
