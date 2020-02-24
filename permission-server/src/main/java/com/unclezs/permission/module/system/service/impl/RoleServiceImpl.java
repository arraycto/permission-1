package com.unclezs.permission.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.common.util.SqlUtil;
import com.unclezs.permission.module.system.mapper.RoleMapper;
import com.unclezs.permission.module.system.model.Role;
import com.unclezs.permission.module.system.model.RoleMenu;
import com.unclezs.permission.module.system.model.dto.BaseDto;
import com.unclezs.permission.module.system.service.RoleMenuService;
import com.unclezs.permission.module.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author Uncle
 * @since 2020-02-15
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private RoleMenuService roleMenuService;

    /**
     * 查询管理员角色
     *
     * @param aid 管理员ID
     * @return /
     */
    @Override
    public List<Role> selectRoleByAdminId(String aid) {
        return super.baseMapper.selectRoleByAdminId(aid);
    }

    /**
     * 分页查询
     *
     * @param page   /
     * @param params /
     * @return /
     */
    @Override
    public Result list(Page<Role> page, BaseDto params) {
        Page<Role> list = SqlUtil
                .likeAll(Role.class, query(), params.getText())
                .orderBy(params.getSort().size() > 0, params.isAsc(), params.getSort().stream().map(StrUtil::toUnderlineCase).toArray(String[]::new))
                .page(page);
        return ResponseResult.okList(list);
    }

    /**
     * 编辑
     *
     * @param role /
     * @return /
     */
    @Override
    public Result edit(Role role) {
        Role oldRole = super.getById(role.getId());
        BeanUtil.copyProperties(role, oldRole, CopyOptions.create().ignoreNullValue());
        super.updateById(oldRole);
        return ResponseResult.ok("编辑角色成功");
    }

    /**
     * 添加
     *
     * @param role /
     * @return /
     */
    @Override
    public Result add(Role role) {
        super.save(role);
        return ResponseResult.ok("添加角色成功");
    }

    /**
     * 删除
     *
     * @param id /
     * @return /
     */
    @Override
    public Result del(String id) {
        if ("1".equals(id)) {
            return ResponseResult.error("超级管理员角色不可以被删除");
        }
        super.removeById(id);
        return ResponseResult.ok("删除角色成功");
    }

    /**
     * 根据ID查询一个
     *
     * @param id /
     * @return /
     */
    @Override
    public Result getOne(String id) {
        return ResponseResult.ok(super.getById(id));
    }

    /**
     * 根据角色ID查询角色有的菜单IDl列表
     *
     * @param roleId 角色ID
     * @return 菜单IDl列表
     */
    @Override
    public Result getRoleMenus(String roleId) {
        List<String> menuIds = super.baseMapper.getRoleMenuIds(roleId);
        return ResponseResult.ok(menuIds);
    }

    /**
     * 根据角色ID更新角色有的菜单IDl列表
     *
     * @param menuIds 菜单ID列表
     * @param roleId  角色ID
     * @return /
     */

    @Override
    @CacheEvict(allEntries = true, cacheNames = {"menu"})
    @Transactional(rollbackFor = Exception.class)
    public Result updateRoleMenus(String roleId, List<String> menuIds) {
        roleMenuService.removeById(roleId);
        roleMenuService.saveBatch(menuIds.stream().map(mid -> new RoleMenu(roleId, mid)).collect(Collectors.toList()));
        return ResponseResult.ok("更新角色菜单成功");
    }
}