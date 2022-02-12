package com.dang.blog.controller;

import com.dang.blog.common.ErrorCode;
import com.dang.blog.common.Result;
import com.dang.blog.common.aop.LogAnnotation;
import com.dang.blog.util.QiniuUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author: srx
 * @date: 2022/2/9 19:48
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private QiniuUtils qiniuUtils;

    /**
     * 上传图片 云存储
     * 记得在七牛云上设置 公开哦
     * 七牛云给的域名只能用30天，过期记得更换哦
     * @param file
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "编辑文章页",operator = "上传文件到七牛云功能")
    public Result upload(@RequestParam("image")MultipartFile file){
        // 原始文件名称 比如上传的叫aa.jpg 拿到的就是 aa.jpg
        String originalFilename = file.getOriginalFilename();

        // 重命名 随机唯一名
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");

        // 上传文件 到七牛云服务器
        boolean upload = qiniuUtils.upload(file, fileName);

        if(upload){
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.failure(ErrorCode.UPLOAD_ERROR.getCode(),ErrorCode.UPLOAD_ERROR.getMsg());
    }

}
