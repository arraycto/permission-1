package com.unclezs.permission.module.system.controller;

import cn.hutool.captcha.ShearCaptcha;
import com.unclezs.permission.common.util.VerifyCodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author uncle
 * @date 2020.02.04 13:24
 */
@RestController
@RequestMapping("auth")
public class LoginController {

    /**
     * 验证码
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @GetMapping("code")
    public void verifyCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ShearCaptcha captcha = VerifyCodeUtil.getCaptcha();
        captcha.createCode();
        String code = captcha.getCode();
        req.getSession().setAttribute("login_code", code);
        captcha.write(resp.getOutputStream());
    }
}
