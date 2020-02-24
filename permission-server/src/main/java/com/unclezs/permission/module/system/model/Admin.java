package com.unclezs.permission.module.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.unclezs.permission.common.annotation.LikeIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author Uncle
 * @since 2020-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Admin implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 是否启用
     */
    private boolean enabled;

    /**
     * 角色列表
     */
    @LikeIgnore
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 角色ID列表
     */
    @LikeIgnore
    @TableField(exist = false)
    private List<String> roleIds;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        grantedAuthorities.addAll(roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList()));
        return grantedAuthorities;
    }

    public List<String> getRoleIds() {
        if (this.roles != null) {
            return this.roles.stream().map(Role::getId).collect(Collectors.toList());
        }
        return null;
    }

    public List<String> roleIds(){
        return this.roleIds;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
