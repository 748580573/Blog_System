package com.heng.blog_system.utils;

import com.heng.blog_system.anno.RedisKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ReflectUtlis {

    /**
     * 获取方法上的所有注释
     */
    public static Annotation[] getMethodAnnotations(Method method){
        return method.getDeclaredAnnotations();
    }

    /**
     * 判断方法上是否存在某个注释
     * @param method
     * @param annotationClass
     * @return
     */
    public static boolean methodHasAnnotation(Method method,Class<? extends Annotation> annotationClass){
        if (method.getAnnotation(annotationClass) != null){
            return true;
        }
        return false;

    }

    /**
     * 判断object能否由clazz攀升
     * @param object
     * @param clazz
     * @return
     */
    public static boolean isAssignableFrom(Class<?> object,Class<?> clazz){
        return clazz.isAssignableFrom(object);
    }
}
