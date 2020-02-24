package com.unclezs.permission.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 根据请求URL需要的角色 判断这个用户是否有这个角色
 *
 * @author uncle
 * @date 2020/2/18 12:11
 */
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
    /**
     * @param authentication   用户信息
     * @param object           /
     * @param configAttributes 需要角色列表
     * @throws AccessDeniedException               /
     * @throws InsufficientAuthenticationException /
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //判断用户是否有这个角色
        for (ConfigAttribute configAttribute : configAttributes) {
            String role = configAttribute.getAttribute();
            if ("NOT_NEED_AUTH".equals(role)) {
                return;
            }

            if ("NEED_AUTH".equals(role)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("尚未登录，请登录!");
                } else {
                    return;
                }
            }
            for (GrantedAuthority haveRole : authentication.getAuthorities()) {
                if (haveRole.getAuthority().equals(role)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足(403 Forbidden)!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
