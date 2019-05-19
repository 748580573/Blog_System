package com.heng.blog_system.dao;

import com.heng.blog_system.bean.Template;

import java.util.List;
import java.util.Map;

public interface TemplateDao {

    Template addTemplate(Map<String,Object> param) throws Exception;

    List<Map> selectTemplateList(Map<String,Object> param) throws Exception;

    Map<String,Object> selectTemplate(Map<String,Object> param) throws Exception;

    int updateTemplate(Map<String,Object> param) throws Exception;

    public int deleteTemplate(Map<String,Object> param) throws Exception;
}
