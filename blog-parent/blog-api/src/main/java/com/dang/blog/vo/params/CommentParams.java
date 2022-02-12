package com.dang.blog.vo.params;

import lombok.Data;

/**
 * @author: srx
 * @date: 2022/2/9 17:38
 */
@Data
public class CommentParams {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;

}
