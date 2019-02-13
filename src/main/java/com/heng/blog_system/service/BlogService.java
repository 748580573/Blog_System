package com.heng.blog_system.service;

import java.util.Map;

public interface BlogService {

    public Map<String,Object> addBlog(Map<String,Object> map);

    public Map<String,Object> selectBlogs(Map<String,Object> form);
}
