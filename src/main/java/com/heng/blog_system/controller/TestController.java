package com.heng.blog_system.controller;

import com.heng.blog_system.dao.BlogDao;
import com.heng.blog_system.dao.RedisCache;
import com.heng.blog_system.service.BlogService;
import com.heng.blog_system.service.UserService;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    RedisCache redisCache;

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @Autowired
    BlogDao blogDao;

    @RequestMapping(value = {"/test"})
    @ResponseBody
    public void test(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        multipartHttpServletRequest.getFileMap();

    }
}
