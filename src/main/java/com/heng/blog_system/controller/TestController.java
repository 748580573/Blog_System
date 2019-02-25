package com.heng.blog_system.controller;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.User;
import com.heng.blog_system.db.BlogDao;
import com.heng.blog_system.db.RedisCache;
import com.heng.blog_system.service.BlogService;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    RedisCache redisCache;

    @Autowired
    BlogService blogService;

    @RequestMapping(value = {"/test"})
    @ResponseBody
    public Map<String,Object> test(HttpServletRequest request) throws IOException {
        System.out.println(        blogService.test());
        Map<String,Object> map = RequestUtil.getFormData(request);
        return map;
    }
}
