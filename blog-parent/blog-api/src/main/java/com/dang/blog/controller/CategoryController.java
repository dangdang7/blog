package com.dang.blog.controller;

import com.dang.blog.common.Result;
import com.dang.blog.common.aop.LogAnnotation;
import com.dang.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: srx
 * @date: 2022/2/9 18:18
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询文章所有分类
     * @return
     */
    @GetMapping
    @LogAnnotation(module = "博客发布",operator = "获取所有文章分类信息")
    public Result categories(){
        return categoryService.findAll();
    }

    /**
     * 顶部导航栏，查询所有文章分类
     * @return
     */
    @GetMapping("detail")
    @LogAnnotation(module = "博客文章分类页",operator = "获取文章分类信息")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }

    /**
     * 顶部导航栏，查询所有文章分类,进入点击
     * @return
     */
    @LogAnnotation(module = "博客文章分类页",operator = "获取文章分类指定文章信息")
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long categoryId){
        return categoryService.findAllDetailById(categoryId);
    }

}
