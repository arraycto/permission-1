import request from '../utils/request'

export default {
    add(data) {
        return request({
            url: `${entity?lower_case}/add`,
            method: 'post',
            data
        })
    },
    page(params) {
        return request({
            url: `${entity?lower_case}/query/list/${r'${params.page}'}/${r'${params.size}'}`,
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
            url: `${entity?lower_case}/del/${r'${id}'}`,
            method: 'delete',
        })
    },
    edit(data) {
        return request({
            url: `${entity?lower_case}/edit`,
            method: 'put',
            data
        })
    },
    getById(id) {
        return request({
            url: `${entity?lower_case}/query/one/${r'${id}'}`,
            method: 'get',
        })
    }
}
