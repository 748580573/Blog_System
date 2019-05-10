package com.heng.blog_system.test;


import com.heng.blog_system.anno.Permission;
import com.heng.blog_system.utils.FreemarkerUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) throws Exception {
        String str = "${name}";
        Map<String,Object> model = new HashMap<>();
        model.put("name", "wuheng");
        System.out.println(FreemarkerUtil.getHtmlByDB(str, model));
    }
}
