package com.dang.blog.controller;

import com.dang.blog.common.aop.LogAnnotation;
import com.dang.blog.service.TagService;
import com.dang.blog.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: srx
 * @date: 2022/2/9 10:22
 */
@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 最热标签
     * @return
     */
    @LogAnnotation(module = "博客首页",operator = "获取最热标签")
    @GetMapping("hot")
    public Result hot(){
        int limit = 6;
        return tagService.hots(limit);
    }

    /**
     * 查找文章所有标签
     * @return
     */
    @GetMapping
    @LogAnnotation(module = "博客发布",operator = "获取文章所有标签")
    public Result findAll(){
        return tagService.findAll();
    }

    /**
     * 顶部导航栏，查询所有文章标签
     * @return
     */
    @GetMapping("detail")
    @LogAnnotation(module = "博客标签页",operator = "获取所有标签信息")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    /**
     * 顶部导航栏，查询所有文章标签,进入点击
     * @return
     */
    @GetMapping("detail/{id}")
    @LogAnnotation(module = "博客标签详情页",operator = "获取标签指定文章信息")
    public Result findAllDetailById(@PathVariable("id") Long tagId){
        return tagService.findAllDetailById(tagId);
    }


}
