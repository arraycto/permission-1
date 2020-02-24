package com.unclezs.permission.module.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.unclezs.permission.common.annotation.LikeIgnore;
import com.unclezs.permission.common.util.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author Uncle
 * @since 2020-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu implements Serializable, TreeNode<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父菜单ID
     */
    private String pid = "0";

    /**
     * 组件名称
     */
    private String component="/Home";

    /**
     * 菜单类型(1侧边栏菜单 2按钮菜单)
     */
    private Integer type=1;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 菜单图标样式
     */
    private String icon;

    /**
     * 请求Path，用于权限
     */
    private String reqUrl;

    /**
     * 这个菜单访问需要的角色列表
     */
    @LikeIgnore
    @JsonIgnore
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 排序
     */
    private Integer orderBy=99;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 自定义属性
     */
    @LikeIgnore
    @TableField(exist = false)
    private Meta meta;

    /**
     * 是否隐藏
     */
    private Boolean hidden = false;
}
