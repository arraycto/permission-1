package com.unclezs.permission.config.security.handler;

import com.unclezs.permission.common.annotation.Log;
import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.util.ResponseJsonUtil;
import com.unclezs.permission.module.system.model.Admin;
import com.unclezs.permission.module.system.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author uncle
 * @date 2020/2/23 20:17
 */
@Component
@AllArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private LogService logService;

    @Log("管理员登陆")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        Admin admin = (Admin) authentication.getPrincipal();
        admin.setPassword(null);
        ResponseJsonUtil.response(resp, ResponseResult.ok("登录成功", admin));
    }
}
