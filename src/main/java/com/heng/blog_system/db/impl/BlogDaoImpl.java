package com.heng.blog_system.db.impl;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.db.BlogDao;
import com.heng.blog_system.db.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BlogDaoImpl  {

    @Autowired
    private CommonDao commonDao;



}
