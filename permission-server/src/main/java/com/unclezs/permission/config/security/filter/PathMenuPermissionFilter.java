package com.unclezs.permission.config.security.filter;

import com.google.common.collect.Lists;
import com.unclezs.permission.module.system.model.Menu;
import com.unclezs.permission.module.system.model.Role;
import com.unclezs.permission.module.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 根据路径判断需要哪些角色
 *
 * @author uncle
 * @date 2020/2/16 19:06
 */
@Component
public class PathMenuPermissionFilter implements FilterInvocationSecurityMetadataSource {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    private MenuService menuService;
    private static final List<String> NEED_AUTH_URL = Lists.newArrayList(
            "/doc.html"
    );

    @Autowired
    public PathMenuPermissionFilter(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 对菜单需要的角色 进行角色判断
     *
     * @param object /
     * @return /
     * @throws IllegalArgumentException /
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String reqUrl = ((FilterInvocation) object).getRequestUrl();
        //需要角色认证的PATH
        List<Menu> menus = menuService.listAllWithRoles();
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getReqUrl(), reqUrl)) {
                return SecurityConfig.createList(menu.getRoles().stream().map(Role::getName).toArray(String[]::new));
            }
        }
        //不需要认证角色，但是需要登陆的PATH
        for (String notNeedAuthPath : NEED_AUTH_URL) {
            if (antPathMatcher.match(notNeedAuthPath, reqUrl)) {
                return SecurityConfig.createList("NEED_AUTH");
            }
        }
        //其余一律可以访问
        return SecurityConfig.createList("NOT_NEED_AUTH");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
