package com.unclezs.permission.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.common.util.TreeUtil;
import com.unclezs.permission.module.system.mapper.MenuMapper;
import com.unclezs.permission.module.system.model.Menu;
import com.unclezs.permission.module.system.model.vo.MenuTreeVo;
import com.unclezs.permission.module.system.service.MenuService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Uncle
 * @since 2020-02-18
 */
@Service
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    /**
     * 查询所有菜单，带角色
     *
     * @return /
     */
    @Override
    @Cacheable(key = "#root.methodName")
    public List<Menu> listAllWithRoles() {
        return super.baseMapper.listAllWithRoles();
    }

    /**
     * 根据管理员ID 查询路由列表
     *
     * @param adminId 管理员ID
     * @return /
     */
    @Override
    @Cacheable(key = "#root.methodName+#root.args[0]")
    public List<Menu> listRoutes(String adminId) {
        List<Menu> menus = super.baseMapper.listRoutes(adminId);
        return TreeUtil.getTree(menus);
    }


    /**
     * 查询
     *
     * @return /
     */
    @Override
    @Cacheable
    public Result listMenus() {
        List<Menu> list = TreeUtil.getTree(super.lambdaQuery().orderByAsc(Menu::getOrderBy, Menu::getPid).list());
        return ResponseResult.ok(Dict.create().set("total", list.size()).set("list", list));
    }

    /**
     * 编辑
     *
     * @param menu /
     * @return /
     */
    @Override
    @CacheEvict(allEntries = true)
    public Result edit(Menu menu) {
        Menu oldMenu = super.getById(menu.getId());
        BeanUtil.copyProperties(menu, oldMenu, CopyOptions.create().ignoreNullValue());
        super.updateById(oldMenu);
        return ResponseResult.ok("更新菜单成功");
    }

    /**
     * 添加
     *
     * @param menu /
     * @return /
     */
    @Override
    @CacheEvict(allEntries = true)
    public Result add(Menu menu) {
        super.save(menu);
        return ResponseResult.ok("添加菜单成功");
    }

    /**
     * 删除
     *
     * @param id /
     * @return /
     */
    @Override
    @CacheEvict(allEntries = true)
    public Result del(String id) {
        removeChildren(Lists.newArrayList(id));
        return ResponseResult.ok("删除菜单成功");
    }

    /**
     * 根据ID查询一个
     *
     * @param id /
     * @return /
     */
    @Override
    @Cacheable
    public Result getOne(String id) {
        return ResponseResult.ok(super.getById(id));
    }

    @Override
    @Cacheable
    public Result menuTree(String type) {
        List<MenuTreeVo> list = super.baseMapper.menuTree(type);
        List<MenuTreeVo> tree = TreeUtil.getTree(list);
        return ResponseResult.ok(tree);
    }

    private void removeChildren(List<String> pids) {
        if (pids.size() == 0) {
            return;
        }
        removeByIds(pids);
        List<String> ids = lambdaQuery().select(Menu::getId).in(Menu::getPid, pids).list().stream().map(Menu::getId).collect(Collectors.toList());
        removeChildren(ids);
    }
}
