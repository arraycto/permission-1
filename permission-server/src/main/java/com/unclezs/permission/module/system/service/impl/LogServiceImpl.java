package com.unclezs.permission.module.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.module.system.mapper.LogMapper;
import com.unclezs.permission.module.system.model.Log;
import com.unclezs.permission.module.system.model.dto.BaseDto;
import com.unclezs.permission.module.system.service.LogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志记录 
 * </p>
 *
 * @author Uncle
 * @date 2020-02-23
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    /**
    * 分页查询
    *
    * @param page   /
    * @param params /
    * @return /
    */
    @Override
    public Result list(Page<Log> page, BaseDto params) {
       Page<Log> list = lambdaQuery().orderByDesc(Log::getCreateTime).page(page);
       return ResponseResult.okList(list);
    }

}

