package com.unclezs.permission.common.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 列表查询
 * @author uncle
 * @date 2020/2/19 9:24
 */
@Setter
@Getter
@AllArgsConstructor
public class ResponseList {
    private List list;
    private Long total;

   public static ResponseList list(Page page){
       return new ResponseList(page.getRecords(),page.getTotal());
   }
}
