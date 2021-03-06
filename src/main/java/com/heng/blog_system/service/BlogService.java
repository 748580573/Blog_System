package com.heng.blog_system.service;


import java.util.Map;

public interface BlogService {

    public Map<String,Object> addBlog(Map<String,Object> map);

    /**
     * 查询多个博客
     * @param form
     * @return
     */
    public Map<String,Object> selectBlogList(Map<String,Object> form);

    /**
     * 查询热门文章
     * @param form
     * @return
     */
    public Map<String,Object> selectHostBlogs(Map<String,Object> form);


    public Map<String,Object> selectNewBlogs(Map<String,Object> form);

    /**
     * 站内搜索
     * @param form
     * @param clazz  映射为es所需的索引名和索引类型
     * @return
     */
    public Map<String,Object> fuzzySearch(Map<String,Object> form,Class<?> clazz);

    /**
     * 通过数据库进行模糊查询
     * @return
     */
    public Map<String,Object> fuzzySearchByDB(Map<String, Object> form);

    public Map<String,Object> selectRecommendBlog(Map<String,Object> form);

    /**
     * 查询单个博客
     * @param form
     * @return
     */
    public Map<String,Object> selectBlog(Map<String,Object> form);

    /**
     * 排行查询
     * @param form
     * @return
     */
    public Map<String,Object> selectBlogForRank(Map<String,Object> form);

    /**
     * 按时间顺序查询博客列表
     * @param form
     * @return
     */
    public Map<String,Object> selevtBlogsOrderByTime(Map<String,Object> form);

    public Map<String,Object> selectAdvBlogs(Map<String,Object> from);


    /**
     * 修改博客
     * @param form
     * @return
     */
    public Map<String,Object> updateBlog(Map<String,Object> form);

    /**
     * 删除博客
     * @param form
     * @return
     */
    public Map<String,Object> deleteBlog(Map<String,Object> form);

    /**
     * 查询所有Tag
     * @param
     * @return
     */
    public Map<String,Object> selectTags();



    public Map<String,Object> selectImgs();

    /**
     * 测试用接口
     * @param form
     * @return
     */
    public Map<String,Object> test(Map<String,Object> form);


}
