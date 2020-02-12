package com.unclezs.permission.common.util;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * HttpServletResponse响应JSON工具
 * @author uncle
 * @date 2020.02.04 13:47
 */
@Slf4j
public class ResponseJsonUtil {
    public static void response(HttpServletResponse resp, Object data) {
        resp.setContentType("application/json;charset=utf-8");
        try {
            PrintWriter writer = resp.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(data));
            writer.close();
        } catch (IOException e) {
            log.error("响应失败,msg:{}", ExceptionUtil.getMessage(e));
            e.printStackTrace();
        }
    }
}
