package com.heng.blog_system.controller;


import com.heng.blog_system.service.TemplateService;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "/addTemplate.html")
    public Map<String,Object> addCarousel(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        return templateService.addTemplate(form);
    }

    @RequestMapping(value = "/template.html")
    public Map<String,Object> Carousel(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        return templateService.selectTemplateById(form);
    }


    @RequestMapping(value = "/templateList.html")
    public Map<String,Object> CarouselList(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        return templateService.selectTemplateList(form);
    }

    @RequestMapping(value = "/carouse.html")
    public Map<String,Object> CarouselHtml(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        return templateService.selectTemplateByHtmlId(form);
    }
}
