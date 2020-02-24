package com.unclezs.permission.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.module.system.model.Menu;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Uncle
 * @since 2020-02-18
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查询所有菜单，带角色
     *
     * @return /
     */
    List<Menu> listAllWithRoles();

    /**
     * 根据管理员ID 查询路由列表
     *
     * @param adminId 管理员ID
     * @return /
     */
    List<Menu> listRoutes(String adminId);

    Result listMenus();

    Result edit(Menu menu);

    Result add(Menu menu);

    Result del(String id);

    Result getOne(String id);

    Result menuTree(String type);
}
