package com.unclezs.permission.module.system.service;

import com.unclezs.permission.module.system.model.Log;
import com.baomidou.mybatisplus.extension.service.IService;

import com.unclezs.permission.common.response.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclezs.permission.module.system.model.dto.BaseDto;

/**
 * 日志记录
 *
 * @author Uncle
 * @date 2020-02-23
 */
public interface LogService extends IService<Log> {

    Result list(Page<Log> page, BaseDto params);
}
