package com.heng.blog_system.service;

import java.util.Map;

public interface TemplateService {

    Map<String,Object> addTemplate(Map<String,Object> form);

    Map<String,Object> selectTemplateByHtmlId(Map<String,Object> form);

    Map<String,Object> selectTemplateList(Map<String,Object> form);

    Map<String,Object> selectTemplateById(Map<String,Object> form);

    Map<String,Object> updataTemplateById(Map<String,Object> form);

    Map<String,Object> delerteTemplateById(Map<String,Object> from);
}
