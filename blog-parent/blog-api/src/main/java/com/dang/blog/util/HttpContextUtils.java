package com.dang.blog.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: srx
 * @date: 2022/2/9 19:32
 */
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
