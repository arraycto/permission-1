import cookie from 'js-cookie'
import routeFormat from "../../utils/routeFormat";
import menuApi from "../../api/menu";

const admin = {
    state: {
        admin: JSON.parse(window.sessionStorage.getItem("admin")),
        //本地路由表加上远程拉下来的
        routes: [],
        remoteRoutes: [],
    },
    mutations: {
        setRoutes: (state, routes) => {
            state.routes = routes;
        },
        setRemoteRoutes: (state, routes) => {
            state.remoteRoutes = routes;
        },
        setAdmin: (state, admin) => {
            state.admin = admin;
        }
    },
    actions: {
        loadRouters({commit, state}, router) {
            return new Promise((resolve, reject) => {
                menuApi.listRouters().then(res => {
                    if (res.success) {
                        let formatRouters = routeFormat(res.data);
                        router.addRoutes(formatRouters);
                        let routes = router.options.routes.concat(formatRouters);
                        commit('setRoutes', routes);
                        commit('setRemoteRoutes', formatRouters);
                        resolve(formatRouters)
                    } else {
                        reject()
                    }
                })
            })
        },
    }
};

export default admin
