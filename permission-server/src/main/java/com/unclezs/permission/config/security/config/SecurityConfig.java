package com.unclezs.permission.config.security.config;

import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.util.ResponseJsonUtil;
import com.unclezs.permission.config.security.LoginAuthenticationEntryPoint;
import com.unclezs.permission.config.security.UrlAccessDecisionManager;
import com.unclezs.permission.config.security.filter.NamePwdAuthenticationFilter;
import com.unclezs.permission.config.security.filter.PathMenuPermissionFilter;
import com.unclezs.permission.config.security.handler.LoginSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring security 配置
 *
 * @author uncle
 * @date 2020.02.04 13:04
 */
@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;
    private PathMenuPermissionFilter pathMenuPermissionFilter;
    private UrlAccessDecisionManager urlAccessDecisionManager;
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Remove the ROLE_ prefix
        return new GrantedAuthorityDefaults("");
    }


    @Bean
    public NamePwdAuthenticationFilter namePwdAuthenticationFilter() throws Exception {
        NamePwdAuthenticationFilter filter = new NamePwdAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler((req, resp, e) -> {
            if (e instanceof BadCredentialsException) {
                ResponseJsonUtil.response(resp, ResponseResult.error("用户名或者密码错误"));
            } else if (e instanceof DisabledException) {
                ResponseJsonUtil.response(resp, ResponseResult.ok("账户被禁用，请联系管理员"));
            } else {
                ResponseJsonUtil.response(resp, ResponseResult.ok("登录失败"));
            }
        });
        return filter;
    }

    private ObjectPostProcessor<FilterSecurityInterceptor> objectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setAccessDecisionManager(urlAccessDecisionManager);
                object.setSecurityMetadataSource(pathMenuPermissionFilter);
                return object;
            }
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //登陆异常处理
                .exceptionHandling().authenticationEntryPoint(loginAuthenticationEntryPoint).and()
                .authorizeRequests()
                //设置菜单权限拦截
                .withObjectPostProcessor(objectPostProcessor())
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .addLogoutHandler((req, resp, authentication) -> {
                    ResponseJsonUtil.response(resp, ResponseResult.ok("注销成功"));
                })
                .permitAll()
                .and().csrf().disable();
        //添加过滤器
        http.addFilterAt(namePwdAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
