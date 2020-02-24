package com.unclezs.permission.module.system.mapper;

import com.unclezs.permission.module.system.model.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.unclezs.permission.module.system.model.vo.MenuTreeVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Uncle
 * @since 2020-02-18
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询所有菜单，带角色
     * @return /
     */
    List<Menu> listAllWithRoles();

    /**
     *  根据管理员ID 查询路由列表
     * @param adminId  管理员ID
     * @return /
     */
    List<Menu> listRoutes(String adminId);

    List<MenuTreeVo> menuTree(String type);
}
