package com.heng.blog_system.dao;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.Tag;

import java.util.List;
import java.util.Map;

public interface BlogDao {

    /**
     * 添加博客
     * @param map
     */
    public void addBlog(Map<String,Object> param) throws Exception;

    public List<Blog> selectBlogForSearch(Map<String,Object> param) throws Exception;

    public Blog selectBlogByCode(Map<String,Object> param);

    public int selectBlogTotal(Map<String,Object> param) throws Exception;

    /**
     * 查询所有tags
     * @return
     */
    public List<Tag> selectTags();

    public List<Blog> selectBlogForDBFuzzy(Map<String,Object> param) throws Exception;

}
