package com.unclezs.permission.service.impl;

import com.unclezs.permission.model.Admin;
import com.unclezs.permission.mapper.AdminMapper;
import com.unclezs.permission.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Uncle
 * @since 2020-02-02
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
