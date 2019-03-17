package com.heng.blog_system.controller;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.service.BlogService;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    /**
     * 站内搜索
     * @param request
     * @return
     */
    @RequestMapping(value = "/fuzzy")
    public Map<String,Object> fuzzySearch(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.fuzzySearch(form, Blog.class);
        return result;
    }

    @RequestMapping(value = "/blogList")
    public Map<String,Object> searchBlog(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        form.put("pageSize", 10);                    //限制页面内容数
        result = blogService.selectBlogList(form);
        return result;
    }

    @RequestMapping(value = "/blogListByTime")
    public Map<String,Object> searchBlogOrderByTime(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        form.put("pageSize", 5);                    //限制页面内容数
        result = blogService.selevtBlogsOrderByTime(form);
        return result;
    }

    @RequestMapping(value = "/searchHotBlog")
    public Map<String,Object> searchHotBlog(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        form.put("clickNumber", true);                   //开启按点击量查询
        form.put("pageNumber", 1);
        form.put("pageSize", 4);
        result = blogService.selectHostBlogs(form);
        return result;
    }

    @RequestMapping(value = "/searchNewBlog")
    public Map<String,Object> searchNewBlogs(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        form.put("newBlog", true);                   //开启按点击量查询
        form.put("pageNumber", 1);
        form.put("pageSize", 6);
        result = blogService.selectNewBlogs(form);
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

    /**
     * 用于博客内容展示(查询某个博客)
     * @param request
     * @return
     */
    @RequestMapping(value = "/searchBlog")
    public Map<String,Object> searchBlogForShow(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.selectBlog(form);
        return result;
    }

    @RequestMapping(value = "/rank")
    public Map<String,Object> searchBlogForRank(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.selectBlogForRank(form);
        return result;
    }

    @RequestMapping(value = "/advs")
    public Map<String,Object> selectAdvs(HttpServletRequest request){
        Map<String,Object> map = RequestUtil.getFormData(request);
        Map<String,Object> result = blogService.selectAdvBlogs(map);
        return result;
    }

    /**
     * 跟新博客
     * @param request
     * @return
     */
    @RequestMapping(value = "/update")
    public Map<String,Object> modifyBlog(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.updateBlog(form);
        return result;
    }

    /**
     * 删除博客
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete")
    public Map<String,Object> deleteBlog(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.deleteBlog(form);
        return result;
    }

    @RequestMapping(value = {"/tags"})
    @ResponseBody
    public Map<String,Object> test(HttpServletRequest request) throws IOException {
        Map<String,Object> map = RequestUtil.getFormData(request);
        Map<String,Object> result = blogService.selectTags();
        return result;
    }

}
