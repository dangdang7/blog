package com.dang.blog.common.aop;

import java.lang.annotation.*;

/**
 * @author: srx
 * @date: 2022/2/9 19:16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operator() default "";

}
