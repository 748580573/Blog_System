package com.heng.blog_system.service.impl;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.Template;
import com.heng.blog_system.dao.BlogDao;
import com.heng.blog_system.dao.TemplateDao;
import com.heng.blog_system.service.TemplateService;
import com.heng.blog_system.utils.FreemarkerUtil;
import com.heng.blog_system.utils.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {
    private Logger logger = Logger.getLogger(TemplateServiceImpl.class);

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private BlogDao blogDao;

    @Override
    public Map<String, Object> addTemplate(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        try {
            Template template = templateDao.addTemplate(form);
            result.put("data",template);
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }

    @Override
    public Map<String, Object> selectTemplateByHtmlId(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        try {
            Map<String,Object> template = templateDao.selectTemplate(form);
            List<Blog> list = blogDao.randomSelectBlog(null);
            Map<String,Object> model = new HashMap<>();
            model.put("model",list);
            String templateHtml = template.get("templateContent").toString();
            String html = FreemarkerUtil.getHtmlByDB(templateHtml, model);
            result.put("data",html);
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }

    @Override
    public Map<String, Object> selectTemplateList(Map<String, Object> from) {
        Map<String,Object> result = new HashMap<>();
        try {
            List<Map> list = templateDao.selectTemplateList(from);
            result.put("data",list );
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }

    @Override
    public Map<String, Object> selectTemplateById(Map<String, Object> from) {
        Map<String,Object> result = new HashMap<>();
        try {
            Map<String,Object> map = templateDao.selectTemplate(from);

            result.put("data",map );
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }

    @Override
    public Map<String, Object> updataTemplateById(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        try {
            templateDao.updateTemplate(form);
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }

    @Override
    public Map<String, Object> delerteTemplateById(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        try {
            templateDao.deleteTemplate(form);
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }
}
