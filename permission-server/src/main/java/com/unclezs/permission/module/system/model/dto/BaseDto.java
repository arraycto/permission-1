package com.unclezs.permission.module.system.model.dto;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 通用
 *
 * @author uncle
 * @date 2020/2/19 8:25
 */
@Setter
@Getter
@NoArgsConstructor
public class BaseDto {
    /**
     * 排序 (排序字段列表)
     */
    List<String> sort= Lists.newArrayList();
    /**
     * 模糊查询文本（全字段模糊，去除静态和标记有@LikeIgnore注解）
     */
    String text;

    /**
     * 排序方式(true:asc,  false:desc)
     */
    boolean asc;
}
