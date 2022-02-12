package com.dang.blog.util;

import com.dang.blog.pojo.SysUser;
import lombok.Data;

/**
 * 线程变量隔离
 * @author: srx
 * @date: 2022/2/9 14:39
 */
@Data
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        USER_THREAD_LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return USER_THREAD_LOCAL.get();
    }

    public static void remove(){
        USER_THREAD_LOCAL.remove();
    }

}
