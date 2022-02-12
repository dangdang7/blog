package com.dang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dang.blog.common.Result;
import com.dang.blog.mapper.CategoryMapper;
import com.dang.blog.pojo.Category;
import com.dang.blog.service.CategoryService;
import com.dang.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 15:53
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据id获得文章种类
     * @param categoryId
     * @return
     */
    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = copy(category);
        return categoryVo;
    }

    /**
     * 查询文章所有分类
     * @return
     */
    @Override
    public Result findAll() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        // 转换为和页面交互的对象
        return Result.success(copyList(categories));
    }

    /**
     * 顶部导航栏，查询所有文章分类
     * @return
     */
    @Override
    public Result findAllDetail() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        // 转换为和页面交互的对象
        return Result.success(copyList(categories));
    }

    /**
     * 顶部导航栏，查询所有文章分类,进入点击
     * @return
     */
    @Override
    public Result findAllDetailById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        return Result.success(copy(category));
    }

    /**
     * 集合对拷 objList --> objVoList
     * @param categoryList
     * @return
     */
    public List<CategoryVo> copyList(List<Category> categoryList){
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    /**
     * 属性对拷 obj --> objVo
     * @param category
     * @return
     */
    public CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        categoryVo.setId(String.valueOf(category.getId()));
        return categoryVo;
    }
}
