import request from '../utils/request'

export default {
    listRouters() {
        return request({
            url: `menu/routers`,
            method: 'get',
        })
    },
    add(data) {
        return request({
            url: `menu/add`,
            method: 'post',
            data
        })
    },
    page(params) {
        return request({
            url: `menu/query/list/${params.page}/${params.size}`,
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
            url: `menu/del/${id}`,
            method: 'delete',
        })
    },
    edit(data) {
        return request({
            url: `menu/edit`,
            method: 'put',
            data
        })
    },
    getById(id) {
        return request({
            url: `menu/query/one/${id}`,
            method: 'get',
        })
    },
    getTree(type) {
        return request({
            url: `menu/query/tree`,
            method: 'get',
            params: {
                type
            }
        })
    }
}
