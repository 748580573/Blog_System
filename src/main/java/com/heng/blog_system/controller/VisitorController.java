package com.heng.blog_system.controller;

import com.heng.blog_system.service.VisitorService;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class VisitorController {

    @Autowired
    VisitorService visitorService;

    @RequestMapping(value = "/visitors")
    public Map<String,Object> selectVisitorOrderByTime(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        Map<String,Object> result = visitorService.selectVisitorOrderByTime(form);
        return result;
    }
}
