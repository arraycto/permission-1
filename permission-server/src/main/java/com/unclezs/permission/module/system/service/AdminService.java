package com.unclezs.permission.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.unclezs.permission.module.system.model.Admin;
import com.unclezs.permission.common.response.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclezs.permission.module.system.model.dto.BaseDto;
import com.unclezs.permission.module.system.model.dto.UserPwdDto;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author Uncle
 * @since 2020-02-04
 */
public interface AdminService extends IService<Admin> {

    Result list(Page<Admin> page, BaseDto params);

    Result edit(Admin admin);

    Result add(Admin admin);

    Result del(String id);

    Result getOne(String id);

    Result updatePwd(UserPwdDto userPwdDto);

    Result updateInfo(Admin admin);
}
