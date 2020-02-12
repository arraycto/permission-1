import request from '../utils/request'

export default {
    login(params) {
        return request({
            url: `/doLogin`,
            method: 'post',
            params
        })
    },
}