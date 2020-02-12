package com.unclezs.permission.config.security.filter;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author uncle
 * @date 2020.02.04 19:26
 */
public class VerifyCodeFilter extends GenericFilterBean {
    private String defaultFilterProcessUrl = "/doLogin";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //post请求并且为doLogin
        if (HttpMethod.POST.matches(request.getMethod().toUpperCase()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            
        }
    }
}
