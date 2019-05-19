package com.heng.blog_system.controller;

import com.heng.blog_system.anno.AccessLog;
import com.heng.blog_system.service.CommentService;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment.html")
    @AccessLog(funcationname = "添加评论")
    public Map<String,Object> addComment(HttpServletRequest request){
        return commentService.addComment(request);
    }

    @RequestMapping(value = "/reply.html")
    @AccessLog(funcationname = "添加回复")
    public Map<String,Object> addReply(HttpServletRequest request){
        return commentService.addReply(request);
    }

    @RequestMapping(value = "/searchComment.html")
    @AccessLog(funcationname = "博客的评论栏查询")
    public Map<String,Object> selectCommentsById(HttpServletRequest request){
        return commentService.selectCommentsById(request);
    }
}
