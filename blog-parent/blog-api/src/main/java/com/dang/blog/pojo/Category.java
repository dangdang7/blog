package com.dang.blog.pojo;

import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/9 14:59
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
