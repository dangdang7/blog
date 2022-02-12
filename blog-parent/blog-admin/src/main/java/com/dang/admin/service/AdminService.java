package com.dang.admin.service;

import com.dang.admin.common.Result;
import com.dang.admin.model.params.PageParams;
import com.dang.admin.pojo.Admin;
import com.dang.admin.pojo.Permission;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/10 11:44
 */
public interface AdminService {

    /**
     * 通过用户名查到admin
     * @param username
     * @return
     */
    Admin findAdminByUsername(String username);

    /**
     * 通过adminId查找permission权限
     * @param adminId
     * @return
     */
    List<Permission> findPermissionById(Long adminId);

    /**
     * 获取前端用户列表
     * @param pageParams
     * @return
     */
    Result listUser(PageParams pageParams);

    /**
     * 用户删除
     * @param id
     * @return
     */
    Result deleteUser(Long id);

    /**
     * 新增用户
     * @param admin
     * @return
     */
    Result addUser(Admin admin);

    /**
     * 更新用户
     * @param admin
     * @return
     */
    Result update(Admin admin);
}
