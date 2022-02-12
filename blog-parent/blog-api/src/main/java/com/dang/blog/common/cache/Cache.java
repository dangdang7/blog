package com.dang.blog.common.cache;

import java.lang.annotation.*;

/**
 * @author: srx
 * @date: 2022/2/10 9:45
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000;

    String name() default "";

}
