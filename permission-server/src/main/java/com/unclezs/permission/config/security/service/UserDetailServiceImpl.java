package com.unclezs.permission.config.security.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.unclezs.permission.module.system.model.Admin;
import com.unclezs.permission.module.system.model.Role;
import com.unclezs.permission.module.system.service.AdminService;
import com.unclezs.permission.module.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 管理员用户信息查询
 * </p>
 *
 * @author Uncle
 * @since 2020-02-04
 */
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final RoleService roleService;
    private final AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getUsername, username));
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //加载权限
        List<Role> roles = roleService.selectRoleByAdminId(admin.getId());
        admin.setRoles(roles);
        return admin;
    }
}
