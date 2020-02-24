package com.unclezs.permission.test;

import cn.hutool.core.util.StrUtil;
import com.unclezs.permission.common.annotation.LikeIgnore;
import com.unclezs.permission.module.system.model.Admin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author uncle
 * @date 2020.02.04 12:40
 */
public class AppTest {
    public static void main(String[] args) throws NoSuchFieldException {
        Field[] fields = Admin.class.getDeclaredFields();
        for (Field column : fields) {
            if (column.getAnnotation(LikeIgnore.class) != null || Modifier.isFinal(column.getModifiers())) {
                continue;
            }
            System.out.println(StrUtil.toUnderlineCase(column.getName()));
        }
    }
}
