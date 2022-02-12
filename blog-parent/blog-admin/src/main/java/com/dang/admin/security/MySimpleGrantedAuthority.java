package com.dang.admin.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author: srx
 * @date: 2022/2/10 12:21
 */
public class MySimpleGrantedAuthority implements GrantedAuthority {

    private String authority;

    private String path;

    public MySimpleGrantedAuthority(){}

    public MySimpleGrantedAuthority(String authority){
        this.authority = authority;
    }

    public MySimpleGrantedAuthority(String authority,String path){
        this.authority = authority;
        this.path = path;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getPath() {
        return path;
    }

}
