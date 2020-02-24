package com.unclezs.permission.module.system.controller;


import com.unclezs.permission.common.annotation.Log;
import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.common.util.SecurityUtil;
import com.unclezs.permission.module.system.model.Admin;
import com.unclezs.permission.module.system.model.Menu;
import com.unclezs.permission.module.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单
 *
 * @author Uncle
 * @date 2020-02-21
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单 ")
@AllArgsConstructor
public class MenuController {
    private MenuService menuService;

    @GetMapping("routers")
    @ApiOperation("前端菜单路由查询")
    public Result routers() {
        Admin admin = SecurityUtil.getCurrentUser();
        List<Menu> list = menuService.listRoutes(admin.getId());
        return ResponseResult.ok(list);
    }

    @PostMapping("query/list/{page}/{size}")
    @ApiOperation("列表")
    public Result pageMenu(@PathVariable Integer page, @PathVariable Integer size) {
        return menuService.listMenus();
    }

    @PutMapping("edit")
    @ApiOperation("编辑")
    @Log("编辑菜单")
    public Result editMenu(@Validated @RequestBody Menu menu) {
        return menuService.edit(menu);
    }

    @PostMapping("add")
    @ApiOperation("添加")
    @Log("添加菜单")
    public Result saveMenu(@Validated @RequestBody Menu menu) {
        return menuService.add(menu);
    }

    @DeleteMapping("del/{id}")
    @ApiOperation("删除")
    @Log("删除菜单")
    public Result removeMenu(@PathVariable String id) {
        return menuService.del(id);
    }

    @GetMapping("query/one/{id}")
    @ApiOperation("根据ID查询一个")
    public Result queryMenuById(@PathVariable String id) {
        return menuService.getOne(id);
    }

    @GetMapping("query/tree")
    @ApiOperation("查询菜单树")
    public Result queryMenuTree(String type) {
        return menuService.menuTree(type);
    }
}

