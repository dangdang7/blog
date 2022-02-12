package com.dang.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dang.admin.common.PageResult;
import com.dang.admin.common.Result;
import com.dang.admin.mapper.AdminMapper;
import com.dang.admin.model.params.PageParams;
import com.dang.admin.service.AdminService;
import com.dang.admin.pojo.Admin;
import com.dang.admin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/10 11:44
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 通过用户名查到admin
     * @param username
     * @return
     */
    @Override
    public Admin findAdminByUsername(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        queryWrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    /**
     * 通过adminId查找permission权限
     * SELECT * FROM `ms_permission` WHERE id in (SELECT permission_id FROM `ms_admin_permission` WHERE admin_id = 1)
     * @param adminId
     * @return
     */
    @Override
    public List<Permission> findPermissionById(Long adminId) {
        return adminMapper.findPermissionById(adminId);
    }

    /**
     * 获取前端用户列表
     * @param pageParams
     * @return
     */
    @Override
    public Result listUser(PageParams pageParams) {
        Page<Admin> page = new Page<>(pageParams.getCurrentPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNoneBlank(pageParams.getQueryString())){
            queryWrapper.eq(Admin::getUsername,pageParams.getQueryString());
        }
        Page<Admin> adminPage = adminMapper.selectPage(page, queryWrapper);
        PageResult<Admin> pageResult = new PageResult<>();
        pageResult.setList(adminPage.getRecords());
        pageResult.setTotal(adminPage.getTotal());
        return Result.success(pageResult);
    }

    /**
     * 用户删除
     * @param id
     * @return
     */
    @Override
    public Result deleteUser(Long id) {
        adminMapper.deleteById(id);
        return Result.success(null);
    }

    /**
     * 新增用户
     * @param admin
     * @return
     */
    @Override
    public Result addUser(Admin admin) {
        admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
        adminMapper.insert(admin);
        return Result.success(null);
    }

    /**
     * 更新用户
     * @param admin
     * @return
     */
    @Override
    public Result update(Admin admin) {
        adminMapper.updateById(admin);
        return Result.success(null);
    }

}
