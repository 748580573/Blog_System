package com.heng.blog_system.dao.impl;

import com.heng.blog_system.dao.CommentDao;
import com.heng.blog_system.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private CommonDao CommonDao;

    @Override
    public void addComment(Map<String, Object> param) {

    }
}
