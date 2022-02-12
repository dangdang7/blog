package com.dang.blog.pojo;

import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/9 14:58
 */
@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
