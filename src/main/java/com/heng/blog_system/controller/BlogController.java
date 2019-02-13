package com.heng.blog_system.controller;

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

    @RequestMapping(value = "/uploadFile")
    public List<String> uploadFile(HttpServletRequest request) throws IOException {
        List<String> filePath = RequestUtil.fileUpLoad(request);
        return filePath;
    }

    @RequestMapping(value = "/editBlog")
    public Map<String,Object> editBlog(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.addBlog(form);
        return result;
    }

    @RequestMapping(value = "/search")
    public Map<String,Object> searchBlog(HttpServletRequest request){
        Map<String,Object> result = null;
        Map<String,Object> form = RequestUtil.getFormData(request);
        result = blogService.selectBlogs(form);
        return result;
    }
}
