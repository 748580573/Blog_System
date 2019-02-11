package com.heng.blog_system.controller;

import com.heng.blog_system.utils.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/blog")
public class BlogController {

    @RequestMapping(value = "/uploadFile")
    public List<String> uploadFile(HttpServletRequest request) throws IOException {
        List<String> filePath = RequestUtil.fileUpLoad(request);
        return filePath;
    }

    @RequestMapping(value = "/editBlog")
    public Map<String,Object> editBlog(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        return null;

    }
}
