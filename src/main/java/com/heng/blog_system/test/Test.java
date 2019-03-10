package com.heng.blog_system.test;


import com.heng.blog_system.anno.Permission;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        String regex = ".*/img/";
        String url = "http://localhost:8080/blog_system/img/login.html";
        Pattern pattern =Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        System.out.println(matcher.find());
    }
}
