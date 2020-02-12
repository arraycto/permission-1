package com.unclezs.permission.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author uncle
 * @date 2020.02.04 13:27
 */
@Data
@AllArgsConstructor
public class ResponseResult {
    private String msg;
    private int code;
    private boolean success;
    private Object data;

    public static ResponseResult ok() {
        return new ResponseResult("请求成功", 20000, true, null);
    }

    public static ResponseResult ok(String msg) {
        return new ResponseResult(msg, 20000, true, null);
    }

    public static ResponseResult ok(String msg, Object data) {
        return new ResponseResult(msg, 20000, true, data);
    }

    public static ResponseResult error() {
        return new ResponseResult("请求失败", 50000, false, null);
    }

    public static ResponseResult error(String msg) {
        return new ResponseResult(msg, 50000, false, null);
    }

    public static ResponseResult error(String msg, Object data) {
        return new ResponseResult(msg, 50000, false, data);
    }
}
