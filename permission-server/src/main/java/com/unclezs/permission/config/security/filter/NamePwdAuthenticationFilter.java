/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.unclezs.permission.config.security.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.util.ResponseJsonUtil;
import com.unclezs.permission.common.util.VerifyCodeUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 用户名 密码 验证码 校验
 *
 * @author uncle
 */
@Getter
public class NamePwdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Value("${rsa.privateKey}")
    private String privateKey;
    public static final String DEFAULT_FILTER_PROCESS_URL = "/login";


    public NamePwdAuthenticationFilter() {
        super(new AntPathRequestMatcher(DEFAULT_FILTER_PROCESS_URL, "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //验证码校验
        String userInputCode = request.getParameter("code");
        String code = Optional.ofNullable(request.getSession().getAttribute("login_code")).orElse("").toString();
        boolean isCodePass = false;
        if (StrUtil.isEmpty(userInputCode)) {
            ResponseJsonUtil.response(response, ResponseResult.error("验证码不能为空"));
        } else if (StrUtil.isEmpty(code)) {
            ResponseJsonUtil.response(response, ResponseResult.error("验证码已失效"));
        } else {
            isCodePass = VerifyCodeUtil.verify(code, userInputCode);
        }
        //验证码校验通过
        if (isCodePass) {
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            } else {
                //公钥加密 私钥解密
                RSA rsa = new RSA(privateKey, null);
                password = rsa.decryptStr(password, KeyType.PrivateKey);
            }
            username = username.trim();
        } else {
            ResponseJsonUtil.response(response, ResponseResult.error("验证码错误"));
            username = "";
            password = "";
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
