package com.unclezs.permission.module.system.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class AdminController {
    @GetMapping("hello")
    public String sayHello() {
        return "hello";
    }
}
