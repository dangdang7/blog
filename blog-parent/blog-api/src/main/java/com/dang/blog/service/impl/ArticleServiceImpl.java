package com.dang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dang.blog.async.ThreadService;
import com.dang.blog.dos.Archives;
import com.dang.blog.mapper.ArticleBodyMapper;
import com.dang.blog.mapper.ArticleMapper;
import com.dang.blog.mapper.ArticleTagMapper;
import com.dang.blog.pojo.Article;
import com.dang.blog.pojo.ArticleBody;
import com.dang.blog.pojo.ArticleTag;
import com.dang.blog.pojo.SysUser;
import com.dang.blog.service.*;
import com.dang.blog.util.UserThreadLocal;
import com.dang.blog.vo.ArticleBodyVo;
import com.dang.blog.vo.ArticleVo;
import com.dang.blog.common.Result;
import com.dang.blog.vo.SysUserVo;
import com.dang.blog.vo.TagVo;
import com.dang.blog.vo.params.ArticleParams;
import com.dang.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: srx
 * @date: 2022/2/9 9:16
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));
    }

    /**
     * 获取最热文章
     * select id,title from `ms_article` order by view_counts desc limit 5
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);

        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList,false,false));
    }

    /**
     * 获取最新文章
     * select id,title from `ms_article` order by create_date desc limit 5
     * @param limit
     * @return
     */
    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);

        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList,false,false));
    }

    /**
     * 文章归档
     * select year(create_date) as year,month(create_date) as month,count(*) as count from ms_article group by year,month
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    /**
     * 获取文章详情信息
     * @param articleId
     * @return
     */
    @Override
    public Result findArticleBodyByArticleId(Long articleId) {
        /**
         * 1、根据文章id，查询文章信息
         * 2、根据Body id 和 Category id做关联查询
         */
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true,true,true);

        // 看完文章后，新增阅读数，有没有问题呢？
        // 查看完文章之后本应该返回数据了，这个时候做一个更新操作，更新是加写锁，就会阻塞其他的读操作，性能就比较低
        // 更新 增加了此次接口的耗时 如果一旦更新出问题不能影响我们查看文章的操作
        // 使用 线程池 可以吧更新操作扔到线程池中去执行 和主线程就不相关了
        threadService.updateArticleViewCount(articleMapper,article);

        return Result.success(articleVo);
    }

    /**
     * 发布文章
     * @param articleParams
     * @return
     */
    @Override
    public Result publish(ArticleParams articleParams) {
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1、发布文章，构建 Article对象
         * 2、作者id 当前登录的用户
         * 3、标签 将标签加入关联列表当中
         * 4、body 内容存储 article bodyId
         */
        Article article = new Article();
        boolean isEdit = false;
        if(articleParams.getId() != null){
            article = new Article();
            article.setId(articleParams.getId());
            article.setTitle(articleParams.getTitle());
            article.setSummary(articleParams.getSummary());
            article.setCategoryId(Long.parseLong(articleParams.getCategory().getId()));
            articleMapper.updateById(article);
            isEdit = true;
        } else {
            article = new Article();
            article.setAuthorId(sysUser.getId());
            article.setWeight(0);
            article.setViewCounts(0);
            article.setTitle(articleParams.getTitle());
            article.setSummary(articleParams.getSummary());
            article.setCommentCounts(0);
            article.setCreateDate(System.currentTimeMillis());
            article.setCategoryId(Long.parseLong(articleParams.getCategory().getId()));
            // 插入之后会生成一个文章id
            articleMapper.insert(article);
        }
        // tags
        List<TagVo> tags = articleParams.getTags();
        if(tags != null){
            for (TagVo tag : tags) {
                Long articleId = article.getId();
//                if(isEdit){
//                    // 先删除
//                    LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
//                    queryWrapper.eq(ArticleTag::getArticleId,articleId);
//                    articleTagMapper.delete(queryWrapper);
//                }
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        // body
        if(isEdit){
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParams.getBody().getContent());
            articleBody.setContentHtml(articleParams.getBody().getContentHtml());
            LambdaUpdateWrapper<ArticleBody> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(ArticleBody::getArticleId,article.getId());
            articleBodyMapper.update(articleBody,updateWrapper);
        } else {
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParams.getBody().getContent());
            articleBody.setContentHtml(articleParams.getBody().getContentHtml());
            articleBodyMapper.insert(articleBody);

            article.setBodyId(articleBody.getId());
            articleMapper.updateById(article);
        }
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        Set<String> keys = redisTemplate.keys("list_article::ArticleController::listArticle::*");
        for (String key : keys) {
            redisTemplate.delete(key);
        }
        return Result.success(map);
    }

    /**
     * 根据bodyId获取body
     * @param bodyId
     * @return
     */
    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();

        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    /**
     * 集合对拷 records -> articleVoList
     * @param records
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    /**
     * 集合对拷 records -> articleVoList (重载)
     * @param records
     * @param isTag
     * @param isAuthor
     * @param isBody
     * @param isCategory
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    /**
     * 属性对拷 record -> articleVo
     * @param article
     * @return
     */
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article,articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        // 并不是所有的接口，都需要标签、作者信息
        if(isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if(isAuthor){
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findUserById(authorId);
            SysUserVo userVo = new SysUserVo();
            userVo.setId(String.valueOf(sysUser.getId()));
            userVo.setNickname(sysUser.getNickname());
            userVo.setAvatar(sysUser.getAvatar());
            articleVo.setAuthor(userVo);
        }
        if(isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if(isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }

        return articleVo;
    }

}
