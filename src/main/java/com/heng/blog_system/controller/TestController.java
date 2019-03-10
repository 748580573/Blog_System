package com.heng.blog_system.controller;

import com.heng.blog_system.db.RedisCache;
import com.heng.blog_system.service.BlogService;
import com.heng.blog_system.service.UserService;
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

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/test"})
    @ResponseBody
    public Map<String,Object> test(HttpServletRequest request) throws IOException {
        Map<String,Object> map = RequestUtil.getFormData(request);
        Map<String,Object> result = blogService.selectAdvBlogs(map);
        return result;
    }
}
