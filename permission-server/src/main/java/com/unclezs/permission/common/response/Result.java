package com.unclezs.permission.common.response;

/**
 * 相应结果
 *
 * @author uncle
 * @date 2020/2/19 9:33
 */
public interface Result {
    String getMsg();

    int getCode();

    boolean isSuccess();

    Object getData();
}
