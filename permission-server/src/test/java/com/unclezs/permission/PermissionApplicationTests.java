package com.unclezs.permission;
//
//import com.unclezs.permission.module.system.mapper.TestMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.util.IdUtil;
import com.unclezs.permission.module.system.model.Admin;
import com.unclezs.permission.module.system.service.AdminService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class PermissionApplicationTests {
    @Autowired
    AdminService adminService;

    @Test
    void contextLoads() {
        System.out.println(HttpMethod.POST.matches("POST"));
    }

    @Test
    void createAdmin() {
        BCryptPasswordEncoder en = new BCryptPasswordEncoder();
        Admin admin = new Admin();
        admin.setEmail("18888888@qq.com");
        admin.setEnabled(true);
        admin.setId(IdUtil.createSnowflake(1, 1).nextId());
        admin.setNickname("李白");
        admin.setPhone("18888888888");
        admin.setPassword(en.encode("uncle"));
        admin.setUsername("test");
        adminService.save(admin);
        System.out.println(adminService.list());
    }
}
