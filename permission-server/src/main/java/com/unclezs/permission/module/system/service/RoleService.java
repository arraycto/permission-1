package com.unclezs.permission.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.module.system.model.Role;
import com.unclezs.permission.module.system.model.dto.BaseDto;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author Uncle
 * @since 2020-02-15
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询管理员角色
     *
     * @param aid /
     * @return /
     */
    List<Role> selectRoleByAdminId(String aid);

    Result list(Page<Role> page, BaseDto params);

    Result edit(Role role);

    Result add(Role role);

    Result del(String id);

    Result getOne(String id);

    Result getRoleMenus(String roleId);

    Result updateRoleMenus(String roleId, List<String> menuIds);
}
