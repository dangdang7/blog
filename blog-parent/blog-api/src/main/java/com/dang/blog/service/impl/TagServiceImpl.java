package com.dang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dang.blog.mapper.TagMapper;
import com.dang.blog.pojo.Tag;
import com.dang.blog.service.TagService;
import com.dang.blog.common.Result;
import com.dang.blog.vo.TagVo;
import org.aspectj.apache.bcel.classfile.annotation.TypeAnnotationGen;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 9:48
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 根据文章id查询文章所有标签
     * @param articleId
     * @return
     */
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        // 由于 tag和article是多对多的关系，三张表，但mybatis是无法进行多表查询的，我们自定义
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    /**
     * 查询最热标签
     * @param limit 查询出来的标签数
     * @return
     */
    @Override
    public Result hots(int limit) {
        /**
         * 1、查询对应文章对多的标签id，就是最热标签
         * 2、根据tag id 进行分组计数，从大到小排列，取前limit个
         * SELECT tag_id FROM `ms_article_tag` group by tag_id order by count(*) desc limit 2
         * 3、拿到tagId 去查找 tag对象
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        List<Tag> tags = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tags);
    }

    /**
     * 查询文章所有标签
     * @return
     */
    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    /**
     * 顶部导航栏，查询所有文章标签
     * @return
     */
    @Override
    public Result findAllDetail() {
        List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }

    /**
     * 顶部导航栏，查询所有文章标签,进入点击
     * @param tagId
     * @return
     */
    @Override
    public Result findAllDetailById(Long tagId) {
        Tag tag = tagMapper.selectById(tagId);
        return Result.success(copy(tag));
    }

    /**
     * 集合对拷 tags -> tagVoList
     * @param tagList
     * @return
     */
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    /**
     * 属性对拷 tag -> tagVo
     * @param tag
     * @return
     */
    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }
}
