package com.dang.admin.security;

import com.dang.admin.service.AdminService;
import com.dang.admin.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/10 11:41
 */
@Component
public class SercurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    /**
     * 当登录的时候，会把username传递到这里
     * 根据username 查询admin类 如果admin存在 将密码告诉springSecurity
     * 如果不存在 返回null 认证失败了
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.findAdminByUsername(username);
        if(admin == null){
            // 获取用户信息失败
            return null;
        }

        // 获取用户信息成功 将用户名、密码传递给SpringSecurity验证登录
        MySimpleGrantedAuthority authority = new MySimpleGrantedAuthority();
        List<MySimpleGrantedAuthority> authorityList = new ArrayList<>();
        UserDetails userDetails = new User(username,admin.getPassword(),authorityList);
        return userDetails;
    }

}
