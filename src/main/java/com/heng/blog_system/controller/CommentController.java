package com.heng.blog_system.controller;

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
    public Map<String,Object> addComment(HttpServletRequest request){
        return commentService.addComment(request);
    }

    @RequestMapping(value = "/reply.html")
    public Map<String,Object> addReply(HttpServletRequest request){
        return commentService.addReply(request);
    }

    @RequestMapping(value = "/searchComment.html")
    public Map<String,Object> selectCommentsById(HttpServletRequest request){
        return commentService.selectCommentsById(request);
    }
}
