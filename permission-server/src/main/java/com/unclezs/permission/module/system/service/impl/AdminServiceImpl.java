package com.unclezs.permission.module.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.unclezs.permission.module.system.model.Admin;
import com.unclezs.permission.module.system.mapper.AdminMapper;
import com.unclezs.permission.module.system.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author Uncle
 * @since 2020-02-04
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = super.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getUsername, username));
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return admin;
    }
}
