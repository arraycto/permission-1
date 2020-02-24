package com.unclezs.permission.module.system.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unclezs.permission.common.util.TreeNode;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author uncle
 * @date 2020/2/21 22:26
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuTreeVo implements Serializable, TreeNode<MenuTreeVo> {
    private String label;
    private List<MenuTreeVo> children;
    private String id;
    private String pid;
}
