package com.unclezs.permission.common.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;

/**
 * 验证码生成工具类
 * @author uncle
 * @date 2020/2/12 17:28
 */
public class VerifyCodeUtil {
    private static  ShearCaptcha captcha;
    static {
        captcha = CaptchaUtil.createShearCaptcha(200, 100);
        captcha.setGenerator(new MathGenerator(1));
    }

    /**
     * 获取验证码工具
     * @return ShearCaptcha
     */
    public static ShearCaptcha getCaptcha(){
        return captcha;
    }

    /**
     * 校验
     * @param code 验证码字符串
     * @param userInputCode 用户输入的
     * @return 是否正确
     */
    public static boolean verify(String code,String userInputCode){
        return captcha.getGenerator().verify(code,userInputCode);
    }
}
