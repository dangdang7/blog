package com.dang.blog.handler;

import com.dang.blog.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ControllerAdvice 对加了Controller注解的类拿到的请求进行拦截处理，AOP
 * @ExceptionHandler 进行异常处理，指定异常处理类型
 * @ResponseBody     返回Result结果的json数据
 * @author: srx
 * @date: 2022/2/9 10:58
 */
@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception e){
        e.printStackTrace();
        return Result.failure(-999,"系统异常");
    }

}
