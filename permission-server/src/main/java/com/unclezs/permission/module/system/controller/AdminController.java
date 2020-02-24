package com.unclezs.permission.module.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclezs.permission.common.annotation.Log;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.common.util.SecurityUtil;
import com.unclezs.permission.module.system.model.Admin;
import com.unclezs.permission.module.system.model.dto.BaseDto;
import com.unclezs.permission.module.system.model.dto.UserPwdDto;
import com.unclezs.permission.module.system.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author Uncle
 * @since 2020-02-04
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员")
@AllArgsConstructor
public class AdminController {
    private AdminService adminService;

    @GetMapping("hello")
    @ApiOperation("测试hello")
    public List<Admin> sayHello() {
        return SecurityUtil.onlineUsers();
    }

    @PostMapping("query/list/{page}/{size}")
    @ApiOperation("分页查询")
    public Result pageAdmin(@PathVariable Integer page, @PathVariable Integer size, @RequestBody BaseDto params) {
        return adminService.list(new Page<>(page, size), params);
    }

    @PutMapping("edit")
    @ApiOperation("编辑")
    @Log("编辑管理员信息")
    public Result editAdmin(@Validated @RequestBody Admin admin) {
        return adminService.edit(admin);
    }

    @PostMapping("add")
    @ApiOperation("添加")
    @Log("添加管理员信息")
    public Result saveAdmin(@Validated @RequestBody Admin admin) {
        return adminService.add(admin);
    }

    @DeleteMapping("del/{id}")
    @ApiOperation("删除")
    @Log("删除管理员信息")
    public Result removeAdmin(@PathVariable String id) {
        return adminService.del(id);
    }

    @GetMapping("query/one/{id}")
    @ApiOperation("根据ID查询一个")
    public Result queryAdminById(@PathVariable String id) {
        return adminService.getOne(id);
    }

    @PutMapping("/updatePwd")
    @ApiOperation("用户密码修改")
    @Log("管理员密码更改")
    public Result updatePwd(@RequestBody UserPwdDto userPwdDto) {
        return adminService.updatePwd(userPwdDto);
    }

    @PutMapping("/updateInfo")
    @ApiOperation("用户资料修改")
    @Log("管理员资料更改")
    public Result updateInfo(@RequestBody Admin admin) {
        return adminService.updateInfo(admin);
    }
}
