package com.unclezs.permission.common.aspect;

import cn.hutool.core.date.DateUtil;
import com.unclezs.permission.common.util.IpUtil;
import com.unclezs.permission.common.util.SecurityUtil;
import com.unclezs.permission.module.system.model.Log;
import com.unclezs.permission.module.system.service.LogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志切面
 *
 * @author uncle
 * @date 2020/2/23 20:03
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class LogAspect {
    private LogService logService;
    private HttpServletRequest request;


    /**
     * 切点
     */
    @Pointcut("@annotation(com.unclezs.permission.common.annotation.Log)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Log log = new Log();
        long currentTime = System.currentTimeMillis();
        Object res = proceedingJoinPoint.proceed();
        //获取当前时间
        log.setSpendTime((int) (System.currentTimeMillis() - currentTime));
        log.setCreateTime(DateUtil.now());
        log.setIp(IpUtil.getClientIpAddress(request));
        log.setUsername(SecurityUtil.getCurrentUser().getUsername());
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String opName = signature.getMethod().getAnnotation(com.unclezs.permission.common.annotation.Log.class).value();
        log.setOpName(opName);
        logService.save(log);
        return res;
    }
}