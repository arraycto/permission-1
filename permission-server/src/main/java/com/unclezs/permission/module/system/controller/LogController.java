package com.unclezs.permission.module.system.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.common.util.SecurityUtil;
import com.unclezs.permission.module.system.model.Log;
import com.unclezs.permission.module.system.model.dto.BaseDto;
import com.unclezs.permission.module.system.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 日志记录
 *
 * @author Uncle
 * @date 2020-02-23
 */
@RestController
@RequestMapping("/log")
@Api(tags = "日志记录 ")
@AllArgsConstructor
public class LogController {
    private LogService logService;

    @PostMapping("query/list/{page}/{size}")
    @ApiOperation("分页查询")
    public Result pageLog(@PathVariable Integer page, @PathVariable Integer size, @RequestBody BaseDto params) {
        return logService.list(new Page<>(page, size), params);
    }

    @DeleteMapping("clear")
    @ApiOperation("清空日志")
    @com.unclezs.permission.common.annotation.Log("清空日志")
    public Result clearLog() {
        if ("1".equals(SecurityUtil.getCurrentUser().getId())) {
            logService.remove(Wrappers.<Log>lambdaUpdate().ne(Log::getCreateTime, DateUtil.date()));
            return ResponseResult.ok("日志已清空");
        }
        return ResponseResult.error("权限不足");
    }
}

