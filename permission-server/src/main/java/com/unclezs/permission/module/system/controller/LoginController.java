package com.unclezs.permission.module.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import com.unclezs.permission.common.response.ResponseResult;
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
public class LoginController {
    @RequestMapping("login")
    public ResponseResult login() {
        return ResponseResult.error("请求失败,请先登录");
    }

    /**
     * 验证码
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @GetMapping("code")
    public void verifyCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100);
        captcha.setGenerator(new MathGenerator(1));
        captcha.createCode();
        String code = captcha.getCode();
        req.getSession().setAttribute("login_code", code);
        System.out.println(code);
        captcha.write(resp.getOutputStream());
    }
}
