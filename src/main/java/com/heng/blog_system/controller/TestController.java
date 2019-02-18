package com.heng.blog_system.controller;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.User;
import com.heng.blog_system.db.BlogDao;
import com.heng.blog_system.db.RedisCache;
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

    @RequestMapping(value = {"/test"})
    @ResponseBody
    public Map<String,Object> test(HttpServletRequest request) throws IOException {
        User user = new User();
        user.setAge(12);
        user.setPath("e://aaa");
        user.setName("uwheng");
        redisCache.put("me", user);
        Object object = redisCache.get("me");
        Map<String,Object> map = RequestUtil.getFormData(request);
        map.put("user", object);
        return map;
    }
}
