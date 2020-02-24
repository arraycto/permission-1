package com.unclezs.permission;

import cn.hutool.core.thread.ThreadUtil;
import com.unclezs.permission.common.util.TreeUtil;
import com.unclezs.permission.module.system.model.Menu;
import com.unclezs.permission.module.system.service.MenuService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
class AppRunnerTests {
    @Autowired
    MenuService tester;
    private int count=0;
    @Test
    void test2(){
        List<Menu> menus = tester.list();
        List<Menu> tree = TreeUtil.getTree(menus);
        System.out.println(tree);
    }
    @Test
    void contextLoads() throws ExecutionException, InterruptedException {
        ArrayList<Future> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            Future<?> future = ThreadUtil.execAsync(() -> {
                count++;
            });
            list.add(future);
        }
        for (Future future : list) {
            future.get();
        }
        System.out.println(count);
    }
}
