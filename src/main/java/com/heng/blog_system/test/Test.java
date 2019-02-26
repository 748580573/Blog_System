package com.heng.blog_system.test;


import com.heng.blog_system.anno.Permission;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) {
        Class<?> clazz = Permission.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods){
            method.setAccessible(true);
            System.out.println(method.getName());
        }

    }
}
