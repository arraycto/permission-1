package com.unclezs.permission.module.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclezs.permission.common.annotation.Log;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.module.system.model.Role;
import com.unclezs.permission.module.system.model.dto.BaseDto;
import com.unclezs.permission.module.system.model.dto.RoleMenuDto;
import com.unclezs.permission.module.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 角色
 *
 * @author Uncle
 * @date 2020-02-20
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色 ")
@AllArgsConstructor
public class RoleController {
    private RoleService roleService;

    @PostMapping("query/list/{page}/{size}")
    @ApiOperation("分页查询")
    public Result pageRole(@PathVariable Integer page, @PathVariable Integer size, @RequestBody BaseDto params) {
        return roleService.list(new Page<>(page, size), params);
    }

    @PutMapping("edit")
    @ApiOperation("编辑")
    @Log("编辑角色")
    public Result editRole(@Validated @RequestBody Role role) {
        return roleService.edit(role);
    }

    @PostMapping("add")
    @ApiOperation("添加")
    @Log("添加角色")
    public Result saveRole(@Validated @RequestBody Role role) {
        return roleService.add(role);
    }

    @DeleteMapping("del/{id}")
    @ApiOperation("删除")
    @Log("删除角色")
    public Result removeRole(@PathVariable String id) {
        return roleService.del(id);
    }

    @GetMapping("query/one/{id}")
    @ApiOperation("根据ID查询一个")
    public Result queryRoleById(@PathVariable String id) {
        return roleService.getOne(id);
    }

    @GetMapping("edit/menu")
    public Result getRoleMenus(String roleId) {
        return roleService.getRoleMenus(roleId);
    }

    @PutMapping("edit/menu")
    @Log("更新角色菜单")
    public Result updateRoleMenus(@RequestBody RoleMenuDto dto) {
        return roleService.updateRoleMenus(dto.getRoleId(), dto.getMenuIds());
    }
}

