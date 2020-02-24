package com.unclezs.permission.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unclezs.permission.module.system.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author Uncle
 * @since 2020-02-15
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查询管理员角色
     * @param aid 管理员ID
     * @return /
     */
    List<Role> selectRoleByAdminId(String aid);

    List<String> getRoleMenuIds(String roleId);
}
