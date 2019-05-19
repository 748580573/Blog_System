package com.heng.blog_system.controller;

import com.heng.blog_system.service.LoggerService;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/logger")
public class LoggerController {

    @Autowired
    private LoggerService loggerService;

    @RequestMapping(value = "/loggerList.html")
    public Map<String,Object> getLoggerList(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        return loggerService.selectLoggerList(form);
    }

    @RequestMapping(value = "/logger.html")
    public Map<String,Object> getLogger(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        return loggerService.selectLogger(form);
    }
}
