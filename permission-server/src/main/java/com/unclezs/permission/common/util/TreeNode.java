package com.unclezs.permission.common.util;

import java.util.List;

/**
 * 树节点
 * @author Uncle
 * @date 2019.10.06.
 */
public interface TreeNode<T> {
    /**
     * 获取父节点ID
     * @return
     */
    String getPid();

    /**
     * 获取自己的ID
     * @return
     */
    String getId();

    /**
     * 添加子节点列表
     * @param node /
     */
    void  setChildren(List<T> node);
}