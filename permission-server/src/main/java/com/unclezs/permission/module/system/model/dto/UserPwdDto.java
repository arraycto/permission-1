package com.unclezs.permission.module.system.model.dto;

import lombok.Data;

/**
 * 密码修改Dto
 * @author uncle
 * @date 2020/2/23 9:26
 */
@Data
public class UserPwdDto {

    private String oldPwd;

    private String newPwd;
}

