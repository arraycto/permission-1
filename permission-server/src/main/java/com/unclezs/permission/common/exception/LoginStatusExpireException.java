package com.unclezs.permission.common.exception;

/**
 * 登陆状态过期
 *
 * @author uncle
 * @date 2020/2/20 16:51
 */
public class LoginStatusExpireException extends RuntimeException {

    @Override
    public String getMessage() {
        return "登陆状态过期，请重新登陆";
    }
}
