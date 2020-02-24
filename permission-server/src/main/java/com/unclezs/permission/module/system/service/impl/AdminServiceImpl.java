package com.unclezs.permission.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclezs.permission.common.response.ResponseResult;
import com.unclezs.permission.common.response.Result;
import com.unclezs.permission.common.util.SecurityUtil;
import com.unclezs.permission.common.util.SpringContextHolder;
import com.unclezs.permission.common.util.SqlUtil;
import com.unclezs.permission.module.system.mapper.AdminMapper;
import com.unclezs.permission.module.system.model.Admin;
import com.unclezs.permission.module.system.model.AdminRole;
import com.unclezs.permission.module.system.model.dto.BaseDto;
import com.unclezs.permission.module.system.model.dto.UserPwdDto;
import com.unclezs.permission.module.system.service.AdminRoleService;
import com.unclezs.permission.module.system.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author Uncle
 * @since 2020-02-04
 */
@Service
@AllArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    private AdminRoleService adminRoleService;

    /**
     * 分页查询
     *
     * @param page   /
     * @param params /
     * @return /
     */
    @Override
    public Result list(Page<Admin> page, BaseDto params) {
        Page<Admin> list = SqlUtil
                .likeAll(Admin.class, query(), params.getText())
                .orderBy(params.getSort().size() > 0, params.isAsc(), params.getSort().stream().map(StrUtil::toUnderlineCase).toArray(String[]::new))
                .page(page);
        return ResponseResult.okList(list);
    }

    /**
     * 编辑
     *
     * @param admin /
     * @return /
     */
    @Override
    public Result edit(Admin admin) {
        Admin oldAdmin = super.getById(admin.getId());
        //删除以前的角色
        adminRoleService.removeById(admin.getId());
        //保存现在选择的角色
        if (admin.roleIds().size() > 0) {
            List<AdminRole> adminRoleList = admin.roleIds().stream().map(roleId -> new AdminRole(admin.getId(), roleId)).collect(Collectors.toList());
            adminRoleService.saveBatch(adminRoleList);
        }
        //提交更新
        BeanUtil.copyProperties(admin, oldAdmin, CopyOptions.create().ignoreNullValue());
        super.updateById(oldAdmin);
        return ResponseResult.ok("更新管理员成功");
    }

    /**
     * 添加
     *
     * @param admin /
     * @return /
     */
    @Override
    public Result add(Admin admin) {
        admin.setId(IdUtil.simpleUUID());
        //保存选择的角色
        List<AdminRole> adminRoleList = admin.roleIds().stream().map(roleId -> new AdminRole(admin.getId(), roleId)).collect(Collectors.toList());
        BCryptPasswordEncoder encoder = SpringContextHolder.getBean(BCryptPasswordEncoder.class);
        admin.setPassword(encoder.encode(admin.getPassword()));
        //保存数据
        super.save(admin);
        adminRoleService.saveBatch(adminRoleList);
        return ResponseResult.ok("添加管理员成功");
    }

    /**
     * 删除
     *
     * @param id /
     * @return /
     */
    @Override
    public Result del(String id) {
        if ("1".equals(id)) {
            return ResponseResult.error("超级管理员不可删除");
        }
        super.removeById(id);
        return ResponseResult.ok("删除管理员成功");
    }

    /**
     * 根据ID查询一个
     *
     * @param id /
     * @return /
     */
    @Override
    public Result getOne(String id) {
        return ResponseResult.ok(super.baseMapper.getOneWithRoles(id));
    }

    /**
     * 修改用户密码
     *
     * @param userPwdDto /
     * @return /
     */
    @Override
    public Result updatePwd(UserPwdDto userPwdDto) {
        Admin admin = lambdaQuery().select(Admin::getId, Admin::getPassword).eq(Admin::getId, SecurityUtil.getCurrentUser().getId()).one();
        BCryptPasswordEncoder encoder = SpringContextHolder.getBean(BCryptPasswordEncoder.class);
        //匹配旧密码
        if (encoder.matches(userPwdDto.getOldPwd(), admin.getPassword())) {
            lambdaUpdate().eq(Admin::getId, admin.getId()).set(Admin::getPassword, encoder.encode(userPwdDto.getNewPwd())).update();
            SecurityUtil.logout();
            return ResponseResult.ok("密码修改成功,请重新登陆");
        }
        return ResponseResult.error("原密码不正确");
    }

    /**
     * 更新用户信息
     *
     * @param admin /
     * @return /
     */
    @Override
    public Result updateInfo(Admin admin) {
        lambdaUpdate().eq(Admin::getId, SecurityUtil.getCurrentUser().getId())
                .set(StrUtil.isNotEmpty(admin.getEmail()), Admin::getEmail, admin.getEmail())
                .set(StrUtil.isNotEmpty(admin.getNickname()), Admin::getNickname, admin.getNickname())
                .set(StrUtil.isNotEmpty(admin.getPhone()), Admin::getPhone, admin.getPhone())
                .update();
        return ResponseResult.ok("个人信息更新成功");
    }
}
