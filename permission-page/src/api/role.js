import request from '../utils/request'

export default {
    add(data) {
        return request({
            url: `role/add`,
            method: 'post',
            data
        })
    },
    page(params) {
        return request({
            url: `role/query/list/${params.page}/${params.size}`,
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
            url: `role/del/${id}`,
            method: 'delete',
        })
    },
    edit(data) {
        return request({
            url: `role/edit`,
            method: 'put',
            data
        })
    },
    getById(id) {
        return request({
            url: `role/query/one/${id}`,
            method: 'get',
        })
    },
    getRoleMenus(roleId) {
        return request({
            url: `role/edit/menu`,
            method: 'get',
            params: {
                roleId
            }
        })
    },
    updateRoleMenus(data) {
        return request({
            url: `role/edit/menu`,
            method: 'put',
            data
        })
    }
}
