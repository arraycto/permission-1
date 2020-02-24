import axios from 'axios'
import {Message} from 'element-ui';
import router from "../router";

let service = axios.create({
    baseURL: process.env.NODE_ENV === 'production' ? process.env.VUE_APP_BASE_API : '/',
    timeout: 3000
});
service.interceptors.response.use(
    res => {
        if (!res.data.success) {
            Message.error(res.data.msg)
            //处理登陆失效或者没有登陆的情况
            if (res.data.code === 44444) {
                router.replace("/login")
            }
        }
        return res.data
    },
    error => {
        let res = error.response;
        if (res.status === 504 || res.status === 404) {
            Message.error("服务器挂了😔")
        } else if (res.status === 403) {
            Message.error("权限不足")
        } else if (res.status === 401) {
            Message.error("还没有登录哦~,请先登录")
        } else {
            if (res.data.msg) {
                Message.error(res.data.msg)
            } else {
                console.log(error)
                Message.error("未知错误")
            }
        }
    }
)

export default service
