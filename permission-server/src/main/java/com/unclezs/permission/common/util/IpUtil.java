package com.unclezs.permission.common.util;

import cn.hutool.core.net.NetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具
 *
 * @author Uncle
 * @date 2019.10.05.
 */
@Slf4j
public class IpUtil {
    private static final String LOCAL_IP = "0:0:0:0:0:0:0:1";


    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"
    };

    public static String getLoaclIpHost() {
        return "http://" + NetUtil.getLocalhost().getHostAddress() + "/";
    }

    /***
     * 获取客户端ip地址(可以穿透代理)
     * @param request /
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request, HEADERS_TO_TRY);
        return ip.equals(LOCAL_IP) ? "127.0.0.1" : ip;
    }
}