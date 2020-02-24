package com.unclezs.permission.common.util;

import com.unclezs.permission.common.exception.LoginStatusExpireException;
import com.unclezs.permission.module.system.model.Admin;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取当前登录的用户
 *
 * @author uncle
 * @date 2020.02.04 13:24
 */
public class SecurityUtil {

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public static Admin getCurrentUser() {
        Admin admin;
        try {
            admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new LoginStatusExpireException();
        }
        return admin;
    }

    /**
     * 注销登陆
     */
    public static void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    /**
     * 获取在线用户信息
     */
    public static List<Admin> onlineUsers() {
        SessionRegistry registry = SpringContextHolder.getBean(SessionRegistry.class);
        return registry.getAllPrincipals().stream().map(p -> (Admin) p).collect(Collectors.toList());
    }
}
