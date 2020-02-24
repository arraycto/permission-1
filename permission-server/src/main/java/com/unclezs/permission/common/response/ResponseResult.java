package com.unclezs.permission.common.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author uncle
 * @date 2020.02.04 13:27
 */
@Data
@AllArgsConstructor
public class ResponseResult implements Serializable, Result {
    private String msg;
    private int code;
    private boolean success;
    private Object data;

    public static ResponseResult ok() {
        return new ResponseResult("success", 20000, true, null);
    }

    public static ResponseResult ok(String msg) {
        return new ResponseResult(msg, 20000, true, null);
    }

    public static ResponseResult ok(String msg, Object data) {
        return new ResponseResult(msg, 20000, true, data);
    }

    public static ResponseResult ok(Object data) {
        return new ResponseResult("success", 20000, true, data);

    }

    public static ResponseResult okList(Page page) {
        return new ResponseResult("success", 20000, true, ResponseList.list(page));
    }

    public static ResponseResult error() {
        return new ResponseResult("failed", 50000, false, null);
    }

    public static ResponseResult error(String msg) {
        return new ResponseResult(msg, 50000, false, null);
    }

    public static ResponseResult error(String msg, Object data) {
        return new ResponseResult(msg, 50000, false, data);
    }
}
