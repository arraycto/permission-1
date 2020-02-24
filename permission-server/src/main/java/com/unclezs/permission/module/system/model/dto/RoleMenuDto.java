package com.unclezs.permission.module.system.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色菜单更新时候的Dto
 *
 * @author uncle
 * @date 2020/2/22 15:06
 */
@Data
public class RoleMenuDto {
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 菜单ID列表
     */
    private List<String> menuIds;
}
