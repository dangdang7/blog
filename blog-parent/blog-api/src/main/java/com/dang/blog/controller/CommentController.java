package com.dang.blog.controller;

import com.dang.blog.common.Result;
import com.dang.blog.common.aop.LogAnnotation;
import com.dang.blog.service.CommentService;
import com.dang.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: srx
 * @date: 2022/2/9 16:55
 */
@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取文章详情页评论信息
     * @param articleId
     * @return
     */
    @LogAnnotation(module = "博客详情页",operator = "获取文章评论信息")
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId){
        return commentService.commentsByArticleId(articleId);
    }

    /**
     * 评论功能
     * @param commentParams
     * @return
     */
    @PostMapping("create/change")
    @LogAnnotation(module = "博客详情页",operator = "评论功能")
    public Result comment(@RequestBody CommentParams commentParams){
        return commentService.comment(commentParams);
    }

}
