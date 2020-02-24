import request from '../utils/request'

export default {
    page(params) {
        return request({
            url: `log/query/list/${params.page}/${params.size}`,
            method: 'post',
            data: {
                asc: params.asc,
                text: params.text,
                sort: params.sort
            }
        })
    },
    clearLogs(){
        return request({
            url: `log/clear`,
            method: 'delete',
        })
    }
}
