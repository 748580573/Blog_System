package com.heng.blog_system.aspect;


import com.heng.blog_system.anno.RedisKey;
import com.heng.blog_system.dao.RedisCache;
import com.heng.blog_system.utils.MapUtils;
import com.heng.blog_system.utils.ReflectUtlis;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

@Component
@Aspect
public class RedisAspect {

    @Autowired
    RedisCache redisCache;

    @Pointcut("@annotation(com.heng.blog_system.anno.RedisKey)")
    public void controller(){}

    @Around("controller()")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        RedisKey annotation = targetMethod.getAnnotation(RedisKey.class);
        System.out.println(annotation.name());
        String methodName = targetMethod.toGenericString();


    }
}
