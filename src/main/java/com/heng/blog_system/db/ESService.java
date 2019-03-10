package com.heng.blog_system.db;

import com.heng.blog_system.bean.Blog;

import java.util.List;
import java.util.Map;

public interface ESService {

    public List<Blog> fuzzySearch(Map<String,Object> param,int pageNo,int pageSize);
}
