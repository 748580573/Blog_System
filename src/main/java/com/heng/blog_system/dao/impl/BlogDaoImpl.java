package com.heng.blog_system.dao.impl;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.Tag;
import com.heng.blog_system.dao.BlogDao;
import com.heng.blog_system.dao.CommonDao;
import com.heng.blog_system.service.BlogService;
import com.heng.blog_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class BlogDaoImpl  implements BlogDao {

    @Autowired
    private CommonDao commonDao;

    @Override
    public Blog addBlog(Map<String, Object> param) throws Exception {
        String json = Utils.objectToJson(param);
        Blog blog = Utils.jsonToObject(json, Blog.class);
        commonDao.save("blogTag.addBlog", blog);
        return blog;
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
    public int selectBlogTotal(Map<String, Object> param) throws Exception {
        return commonDao.get("blogTag.selectBlogTotal", param);
    }

    @Override
    public List<Tag> selectTags() {
        return commonDao.getList("blogTag.selectAllTag");
    }

    @Override
    public List<Blog> selectBlogForDBFuzzy(Map<String, Object> param) throws Exception{
        return commonDao.getList("blogTag.selectBlogForSearch",param);
    }

    @Override
    public Tag addTag(Map<String, Object> param) throws Exception {
        String json = Utils.objectToJson(param);
        Tag tag = Utils.jsonToObject(json,Tag.class);
        commonDao.save("blogTag.addTag", param);
        return tag;
    }

    @Override
    public int addBlogAndTag(Map<String, Object> param) throws Exception {
        return commonDao.save("blogTag.insertTagBlog", param);
    }

    @Override
    public Map<String, Object> selectTagBlog(Map<String, Object> param) throws Exception {
        return commonDao.get("blogTag.selectTagBlog", param);
    }

    @Override
    public int updateBlog(Map<String, Object> param) throws Exception {
        return commonDao.update("blogTag.updateBlog", param);
    }

    @Override
    public List<String> selectImgs() throws Exception {
        return commonDao.getList("blogTag.selectImgs");
    }

    @Override
    public List<Blog> randomSelectBlog(Map<String, Object> param) throws Exception {
        return commonDao.getList("blogTag.randomSelectBlogs",param);
    }
}
