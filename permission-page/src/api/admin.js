import request from '../utils/request'

export default {
    login(params) {
        return request({
            url: `login`,
            method: 'post',
            params
        })
    },
    logout() {
        return request({
            url: `logout`,
            method: 'get',
        })
    },
    add(data) {
        return request({
            url: `admin/add`,
            method: 'post',
            data
        })
    },
    page(params) {
        return request({
            url: `admin/query/list/${params.page}/${params.size}`,
            method: 'post',
            data: {
                asc: params.asc,
                text: params.text,
                sort: params.sort
            }
        })
    },
    delete(id) {
        return request({
            url: `admin/del/${id}`,
            method: 'delete',
        })
    },
    edit(data) {
        return request({
            url: `admin/edit`,
            method: 'put',
            data
        })
    },
    getById(id) {
        return request({
            url: `admin/query/one/${id}`,
            method: 'get',
        })
    },
    updatePwd(data){
        return request({
            url: `admin/updatePwd/`,
            method: 'put',
            data
        })
    },
    updateInfo(data){
        return request({
            url: `admin/updateInfo/`,
            method: 'put',
            data
        })
    }
}
