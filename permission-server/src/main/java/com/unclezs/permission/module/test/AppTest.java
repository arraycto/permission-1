package com.unclezs.permission.module.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author uncle
 * @date 2020/2/22 10:28
 */
public class AppTest {
    public static void main(String[] args) {
        List<String> list = FileUtil.readLines("test.html", Charset.forName("UTF-8"));
        String s = JSONUtil.toJsonStr(list);
        FileUtil.writeString(s,"D:\\java\\permission\\permission-server\\src\\main\\resources\\icon.json","utf-8");
    }
}
