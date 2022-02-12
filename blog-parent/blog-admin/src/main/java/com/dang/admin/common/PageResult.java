package com.dang.admin.common;

import lombok.Data;

import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/10 11:11
 */
@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;

}
