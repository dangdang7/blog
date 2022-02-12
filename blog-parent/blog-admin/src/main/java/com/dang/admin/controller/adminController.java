package com.dang.admin.controller;

import com.dang.admin.common.Result;
import com.dang.admin.model.params.PageParams;
import com.dang.admin.pojo.Admin;
import com.dang.admin.service.AdminService;
import com.dang.admin.service.PermissionService;
import com.dang.admin.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author: srx
 * @date: 2022/2/10 10:59
 */
@RestController
@RequestMapping("admin")
public class adminController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AdminService adminService;

    /**
     * 获取前端权限列表
     * @param pageParams
     * @return
     */
    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParams pageParams){
        return permissionService.listPermission(pageParams);
    }

    /**
     * 权限增加
     * @param permission
     * @return
     */
    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    /**
     * 权限更新
     * @param permission
     * @return
     */
    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    /**
     * 权限删除
     * @param id
     * @return
     */
    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }

    /**
     * 登录页获取登录用户信息
     * @return
     */
    @GetMapping("user/userInfo")
    public Result userInfo(){
        //获取到登录的用户名 这里的User对象是Spring-Security提供的User
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null){
            return Result.success("默认昵称");
        }
        return Result.success(user.getUsername());
    }

    /**
     * 获取前端用户列表
     * @param pageParams
     * @return
     */
    @PostMapping("user/userList")
    public Result listUser(@RequestBody PageParams pageParams){
        return adminService.listUser(pageParams);
    }

    /**
     * 用户删除
     * @param id
     * @return
     */
    @GetMapping("user/delete/{id}")
    public Result deleteUser(@PathVariable("id") Long id){
        return adminService.deleteUser(id);
    }

    /**
     * 新增用户
     * @param admin
     * @return
     */
    @PostMapping("user/add")
    public Result addUser(@RequestBody Admin admin){
        return adminService.addUser(admin);
    }

    /**
     * 更新用户
     * @param admin
     * @return
     */
    @PostMapping("user/update")
    public Result distribute(@RequestBody Admin admin){
        return adminService.update(admin);
    }

}
