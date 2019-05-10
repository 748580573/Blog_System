package com.heng.blog_system.service;

import java.util.Map;

public interface TemplateService {

    Map<String,Object> addTemplate(Map<String,Object> form);

    Map<String,Object> selectTemplateByHtmlId(Map<String,Object> form);

    Map<String,Object> selectTemplateList(Map<String,Object> from);

    Map<String,Object> selectTemplateById(Map<String,Object> from);
}
