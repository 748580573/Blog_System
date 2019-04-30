package com.heng.blog_system.dao;

import com.heng.blog_system.bean.Comment;
import com.heng.blog_system.bean.Reply;

import java.util.List;
import java.util.Map;

public interface CommentDao {

    Comment addComment(Map<String,Object> param) throws Exception;

    Reply addReply(Map<String,Object> param) throws Exception;

    List<Comment> selectComments(Map<String,Object> param) throws Exception;

    List<Reply> selectReplys(Map<String,Object> param) throws Exception;
}
