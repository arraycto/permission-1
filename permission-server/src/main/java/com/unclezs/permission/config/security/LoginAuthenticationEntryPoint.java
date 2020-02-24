package com.unclezs.permission.config.security;

import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.util.ResponseJsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆认证失败的处理
 *
 * @author uncle
 * @date 2020/2/14 12:15
 */
@Component
public class LoginAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseJsonUtil.response(response, ResponseResult.error("权限不足(403 Forbidden)"));
    }
}
