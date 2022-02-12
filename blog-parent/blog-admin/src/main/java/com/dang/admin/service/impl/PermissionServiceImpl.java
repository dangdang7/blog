package com.dang.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dang.admin.common.PageResult;
import com.dang.admin.common.Result;
import com.dang.admin.mapper.PermissionMapper;
import com.dang.admin.model.params.PageParams;
import com.dang.admin.service.PermissionService;
import com.dang.admin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: srx
 * @date: 2022/2/10 11:06
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 获取权限列表
     * @param pageParams
     * @return
     */
    @Override
    public Result listPermission(PageParams pageParams) {
        Page<Permission> page = new Page<>(pageParams.getCurrentPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNoneBlank(pageParams.getQueryString())){
            queryWrapper.eq(Permission::getName,pageParams.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    /**
     * 权限增加
     * @param permission
     * @return
     */
    @Override
    public Result add(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);
    }

    /**
     * 权限更新
     * @param permission
     * @return
     */
    @Override
    public Result update(Permission permission) {
        permissionMapper.updateById(permission);
        return Result.success(null);
    }

    /**
     * 权限删除
     * @param id
     * @return
     */
    @Override
    public Result delete(Long id) {
        permissionMapper.deleteById(id);
        return Result.success(null);
    }

}
