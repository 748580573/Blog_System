package com.heng.blog_system.controller;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.service.BlogService;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @RequestMapping(value = "/editBlog")
    public Map<String,Object> editBlog(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.addBlog(form);
        return result;
    }

    @RequestMapping(value = "/fuzzy")
    public Map<String,Object> fuzzySearch(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.fuzzySearch(form, Blog.class);
        return result;
    }

    @RequestMapping(value = "/search")
    public Map<String,Object> searchBlog(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.selectBlogs(form);
        return result;
    }

    @RequestMapping(value = "/searchHotBlog")
    public Map<String,Object> searchHotBlog(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        form.put("clickNumber", true);                   //开启按点击量查询

        result = blogService.selectHosBlogs(form);
        return result;
    }

    @RequestMapping(value = "/searchNewBlog")
    public Map<String,Object> searchNewBlogs(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        form.put("newBlog", true);                   //开启按点击量查询
        result = blogService.selectHosBlogs(form);
        return result;
    }

    /**
     * 推荐栏
     * @param request
     * @return
     */
    @RequestMapping(value = "/searchRecommendBlog")
    public Map<String,Object> searchRecommend(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.selectRecommendBlog(form);
        return result;

    }

}
