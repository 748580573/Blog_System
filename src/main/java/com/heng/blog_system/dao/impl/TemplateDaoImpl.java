package com.heng.blog_system.dao.impl;

import com.heng.blog_system.bean.Template;
import com.heng.blog_system.dao.CommonDao;
import com.heng.blog_system.dao.TemplateDao;
import com.heng.blog_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TemplateDaoImpl implements TemplateDao {

    @Autowired
    private CommonDao commonDao;
    @Override
    public Template addTemplate(Map<String, Object> param) throws Exception{
        String json = Utils.objectToJson(param);
        Template template = Utils.jsonToObject(json,Template.class);
        commonDao.save("template.addTemplate", template);
        return template;
    }

    @Override
    public List<Map> selectTemplateList(Map<String, Object> param) throws Exception {
        return commonDao.getList("template.selectTemplate",param);
    }

    @Override
    public Map<String, Object> selectTemplate(Map<String, Object> param) throws Exception {
        return commonDao.get("template.selectTemplate",param);
    }
}
