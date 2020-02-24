package com.unclezs.permission.module.system.mapper;

import com.unclezs.permission.module.system.model.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 管理员 Mapper 接口
 * </p>
 *
 * @author Uncle
 * @since 2020-02-04
 */
public interface AdminMapper extends BaseMapper<Admin> {

    Admin getOneWithRoles(String adminId);

}
