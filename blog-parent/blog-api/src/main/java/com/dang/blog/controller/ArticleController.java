package com.dang.blog.controller;

import com.dang.blog.common.aop.LogAnnotation;
import com.dang.blog.common.cache.Cache;
import com.dang.blog.service.ArticleService;
import com.dang.blog.common.Result;
import com.dang.blog.vo.params.ArticleParams;
import com.dang.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: srx
 * @date: 2022/2/9 9:06
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module="博客首页",operator="获取文章列表")      // 加上此注解，代表要对此接口记录日志
    @Cache(expire = 5 * 60 * 1000,name = "list_article" )       // 加上此注解，代表要对此接口得到的数据放入缓存、并指定过期时间
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章
     * @return
     */
    @PostMapping("hot")
    @LogAnnotation(module = "博客首页",operator = "获取最热文章")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article" )
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章
     * @return
     */
    @PostMapping("new")
    @LogAnnotation(module = "博客首页",operator = "获取最新文章")
    @Cache(expire = 5 * 60 * 1000,name = "new_article" )
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("listArchives")
    @LogAnnotation(module = "博客首页",operator = "获取文章归档")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 获取文章详情信息
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    @LogAnnotation(module = "博客详情页",operator = "获取文章详情信息")
    public Result findArticleBodyById(@PathVariable("id") Long articleId){
        return articleService.findArticleBodyByArticleId(articleId);
    }

    /**
     * 发布文章
     * @param articleParams
     * @return
     */
    @PostMapping("publish")
    @LogAnnotation(module = "创建博客页",operator = "发布文章")
    public Result publish(@RequestBody ArticleParams articleParams){
        return articleService.publish(articleParams);
    }

    /**
     * 编辑文章详情信息
     * @param articleId
     * @return
     */
    @PostMapping("{id}")
    @LogAnnotation(module = "博客详情页",operator = "编辑文章内容")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleBodyByArticleId(articleId);
    }
}
