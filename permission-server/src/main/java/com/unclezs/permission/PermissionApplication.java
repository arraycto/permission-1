package com.unclezs.permission;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Uncle
 * @date 2020.02.04 12:40
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.unclezs.permission.module.system.mapper"})
public class PermissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionApplication.class, args);
    }

}
