package com.dang.blog.pojo;

import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/9 18:48
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
