package com.heng.blog_system.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CommentService {

    Map<String,Object> addComment(HttpServletRequest request);

    Map<String,Object> addReply(HttpServletRequest request);

    Map<String,Object> selectCommentsById(HttpServletRequest request);
}
