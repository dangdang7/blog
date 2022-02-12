package com.dang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dang.blog.async.ThreadService;
import com.dang.blog.common.Result;
import com.dang.blog.mapper.ArticleMapper;
import com.dang.blog.mapper.CommentMapper;
import com.dang.blog.pojo.Comment;
import com.dang.blog.pojo.SysUser;
import com.dang.blog.service.CommentService;
import com.dang.blog.service.SysUserService;
import com.dang.blog.util.UserThreadLocal;
import com.dang.blog.vo.CommentVo;
import com.dang.blog.vo.SysUserVo;
import com.dang.blog.vo.params.CommentParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: srx
 * @date: 2022/2/9 16:57
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取相应文章id的评论
     * @param articleId
     * @return
     */
    @Override
    public Result commentsByArticleId(Long articleId) {
        /**
         * 1、根据文章id 在`ms_comment`表中查询相应的评论
         * 2、拿到作者id 查询作者的信息
         * 3、判断 level=1 时要判断有没有子评论
         * 4、再次根据评论id进行查询（parent_id）
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    /**
     * 评论功能实现
     * @param commentParams
     * @return
     */
    @Override
    public Result comment(CommentParams commentParams) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParams.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParams.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        // 评论数+1
        threadService.updateArticleCommentCount(articleMapper,comment.getArticleId());
        return Result.success(null);
    }

    /**
     * 集合对拷 comments --> commentVoList
     * @param comments
     * @return
     */
    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    /**
     * 属性对拷 comment --> commentVo
     * @param comment
     * @return
     */
    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        // 作者信息
        Long authorId = comment.getAuthorId();
        SysUser sysUser = sysUserService.findUserById(authorId);
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser,sysUserVo);
        sysUserVo.setId(String.valueOf(sysUser.getId()));
        commentVo.setAuthor(sysUserVo);
        // 子评论信息
        Integer level = comment.getLevel();
        if(1 == level){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentsId(id);
            commentVo.setChildrens(commentVoList);
        }
        // toUser 给谁评论
        if(level > 1){
            Long toUid = comment.getToUid();
            SysUser user = sysUserService.findUserById(toUid);
            SysUserVo userVo = new SysUserVo();
            BeanUtils.copyProperties(user,userVo);
            userVo.setId(String.valueOf(user.getId()));
            commentVo.setToUser(userVo);
        }
        return commentVo;
    }

    /**
     * 根据父评论id查询子评论信息
     * @param id
     * @return
     */
    private List<CommentVo> findCommentsByParentsId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentMapper.selectList(queryWrapper));
    }
}
