package com.unclezs.permission.config.security;

import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.util.ResponseJsonUtil;
import com.unclezs.permission.module.system.model.Admin;
import com.unclezs.permission.module.system.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring security 配置
 *
 * @author uncle
 * @date 2020.02.04 13:04
 */
@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AdminService adminService;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/code")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler((req, resp, authentication) -> {
                    Admin admin = (Admin) authentication.getPrincipal();
                    admin.setPassword(null);
                    ResponseJsonUtil.response(resp, ResponseResult.ok("登录成功", admin));
                })
                .failureHandler((req, resp, e) -> {
                    if (e instanceof BadCredentialsException) {
                        ResponseJsonUtil.response(resp, ResponseResult.error("用户名或者密码错误"));
                    } else if (e instanceof DisabledException) {
                        ResponseJsonUtil.response(resp, ResponseResult.ok("账户被禁用，请联系管理员"));
                    } else {
                        ResponseJsonUtil.response(resp, ResponseResult.ok("登录失败"));
                    }
                })
                .permitAll()
                .and()
                .logout()
                .addLogoutHandler((req, resp, authentication) -> {
                    ResponseJsonUtil.response(resp, ResponseResult.ok("注销成功"));
                })
                .permitAll()
                .and()
                .csrf()
                .disable();
    }
}
