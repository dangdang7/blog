package com.dang.admin.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/10 11:00
 */
@Data
@AllArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;


    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result failure(int code, String msg){
        return new Result(false,code,msg,null);
    }
}
