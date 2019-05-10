package com.heng.blog_system.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerUtil {

    private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_20);
    static {
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setNumberFormat("#");
    }

    public static String getHtml(String templatePage, Map<String,Object> model) throws Exception {
        Template template = cfg.getTemplate("/src/main/resources" + templatePage);
        StringWriter writer = new StringWriter();
        template.process(model, writer);
        return writer.toString();
    }

    /**
     * 通过自定义字符串模板进行数据渲染
     * @param templateString
     * @param model
     * @return
     * @throws Exception
     */
    public static String getHtmlByDB(String templateString,Object model) throws Exception{
        StringWriter writer = new StringWriter();
        StringTemplateLoader templateLoaders = new StringTemplateLoader();
        templateLoaders.putTemplate("template", templateString);
        cfg.setTemplateLoader(templateLoaders);

        Template template = cfg.getTemplate("template");
        template.process(model,writer);
        return writer.toString();
    }
}
