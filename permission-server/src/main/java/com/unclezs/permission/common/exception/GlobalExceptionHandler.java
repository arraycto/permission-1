package com.unclezs.permission.common.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.unclezs.permission.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全家异常处理器
 *
 * @author uncle
 * @date 2020/2/12 17:44
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全部异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception ex) {
        log.error(ExceptionUtil.stacktraceToString(ex));
        return ResponseResult.error("服务器繁忙");
    }

    /**
     * 登陆异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseResult handleException(AuthenticationServiceException ex) {
        log.error(ExceptionUtil.stacktraceToString(ex));
        return ResponseResult.error(ex.getMessage());
    }

    /**
     * 登陆状态过期异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(LoginStatusExpireException.class)
    public ResponseResult handleException(LoginStatusExpireException ex) {
        log.error(ExceptionUtil.stacktraceToString(ex));
        return new ResponseResult(ex.getMessage(), 44444, false, null);
    }

    /**
     * 参数校验处理
     *
     * @param ex /
     * @return /
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleException(MethodArgumentNotValidException ex) {
        log.error(ExceptionUtil.stacktraceToString(ex));
        String errorMsg = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        return ResponseResult.error(errorMsg);
    }
}
