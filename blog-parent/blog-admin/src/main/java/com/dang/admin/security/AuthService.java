package com.dang.admin.security;

import com.dang.admin.service.AdminService;
import com.dang.admin.pojo.Admin;
import com.dang.admin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/10 11:53
 */
@Service
public class AuthService {

    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication){
        // 权限认证
        // 请求路径
        String requestURI = request.getRequestURI();

        // 拿到当前登录的用户
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }
        // 丛数据库中获取当前登录用户的所有信息
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUsername(username);

        if(admin == null){
            return false;
        }
        // 默认序号为1 的是超级管理员，不用做权限认证了
        if(1 == admin.getId()){
            return true;
        }
        // 查询这个用户的所有权限，判断是否存在当前请求的权限，存在则放行
        Long id = admin.getId();
        List<Permission> permissions = adminService.findPermissionById(id);
        requestURI = StringUtils.split(requestURI,"?")[0];
        for (Permission permission : permissions) {
            if(requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return false;
    }

}
