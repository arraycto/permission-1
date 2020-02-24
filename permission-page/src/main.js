import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import store from './store'
import VueParticles from 'vue-particles'

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(VueParticles);
//全局路由拦截器
router.beforeEach((to, from, next) => {
    if (to.path === '/login') {
        next()
    } else if (to.path==='/') {
        next('/home')
    } else {
        //session有效
        if (window.sessionStorage.getItem("admin")) {
            //正常情况下跳转
            if (store.getters.remoteRoutes.length > 0) {
                next();
            } else {
                //页面刷新Vuex数据重新加载
                store.dispatch("loadRouters", router).then(res => {
                    next({path: to.path, replace: true})
                })
            }
        } else {
            next("/login?from=" + to.path)
        }
    }
});

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')

