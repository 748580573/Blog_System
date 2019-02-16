package com.heng.blog_system.service;

import java.util.Map;

public interface BlogService {

    public Map<String,Object> addBlog(Map<String,Object> map);

    /**
     * 查询多个博客
     * @param form
     * @return
     */
    public Map<String,Object> selectBlogs(Map<String,Object> form);

    /**
     * 查询热门文章
     * @param form
     * @return
     */
    public Map<String,Object> selectHosBlogs(Map<String,Object> form);

    /**
     * 站内搜索
     * @param form
     * @param clazz  映射为es所需的索引名和索引类型
     * @return
     */
    public Map<String,Object> fuzzySearch(Map<String,Object> form,Class<?> clazz);

    public Map<String,Object> selectRecommendBlog(Map<String,Object> form);

    /**
     * 查询单个博客
     * @param form
     * @return
     */
    public Map<String,Object> selectBlog(Map<String,Object> form);

}
