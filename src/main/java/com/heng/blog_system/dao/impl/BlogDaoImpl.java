package com.heng.blog_system.dao.impl;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.Tag;
import com.heng.blog_system.dao.BlogDao;
import com.heng.blog_system.dao.CommonDao;
import com.heng.blog_system.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BlogDaoImpl  implements BlogDao {

    @Autowired
    private CommonDao commonDao;

    @Override
    public void addBlog(Map<String, Object> param) throws Exception {

    }

    @Override
    public List<Blog> selectBlogForSearch(Map<String, Object> param) throws Exception {
        return null;
    }

    @Override
    public Blog selectBlogByCode(Map<String, Object> param) {
        return null;
    }

    @Override
    public int selectBlogTotal() throws Exception {
        return 0;
    }

    @Override
    public List<Tag> selectTags() {
        return commonDao.getList("blogTag.selectAllTag");
    }
}
