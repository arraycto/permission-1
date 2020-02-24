package com.unclezs.permission.common.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.unclezs.permission.common.annotation.LikeIgnore;
import com.unclezs.permission.module.system.model.Admin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * SQL工具类
 *
 * @author uncle
 * @date 2020/2/19 8:56
 */
public class SqlUtil {
    /**
     * 全字段查询
     *
     * @param clazz 实体
     * @param query QueryWrapper
     * @param text  文本
     * @param <T>   /
     */
    public static <T> QueryChainWrapper<T> likeAll(Class clazz, QueryChainWrapper<T> query, String text) {
        if (StrUtil.isNotEmpty(text)) {
            try {
                Field[] fields = Admin.class.getDeclaredFields();
                int i = 0;
                for (Field column : fields) {
                    if (i != 0) {
                        query.or();
                    }
                    //成员不为常量并且没有加LikeIgnore注解
                    query.like(column.getAnnotation(LikeIgnore.class) == null && !Modifier.isFinal(column.getModifiers()), StrUtil.toUnderlineCase(column.getName()), text);
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return query;
    }
}
