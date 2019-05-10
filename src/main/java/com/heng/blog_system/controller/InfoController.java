package com.heng.blog_system.controller;

import com.heng.blog_system.service.InfoIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/info")
public class InfoController {

    @Autowired
    private InfoIService infoIService;

    @RequestMapping(value = "/systemInfo.html")
    public Map<String,Object> getSystemInfo(){
        return infoIService.getSystemInfo();
    }

    @RequestMapping(value = "/systemRate.html")
    public Map<String,Object> getSystemRate(){
        return infoIService.getSystemRate();
    }
}
