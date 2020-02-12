package com.unclezs.permission.module.system.service;

import com.unclezs.permission.module.system.model.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author Uncle
 * @since 2020-02-04
 */
public interface AdminService extends IService<Admin>, UserDetailsService {

}
