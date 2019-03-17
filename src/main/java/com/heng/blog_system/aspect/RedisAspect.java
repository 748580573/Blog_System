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

import java.lang.reflect.Method;
import java.util.Map;

@Component
@Aspect
public class RedisAspect {

    @Autowired
    RedisCache redisCache;

    @Pointcut("execution(* com.heng.blog_system.service.impl..*.*(..))")
    public void controller(){}

    @Around("controller()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        String methodName = targetMethod.toGenericString();
        boolean savaRedis = ReflectUtlis.methodHasAnnotation(targetMethod, RedisKey.class);
        if (savaRedis){
            Object cache = redisCache.get(methodName);
            if (cache != null){
                return cache;
            }
        }else {
            return jp.proceed();
        }


        Object result =jp.proceed();
        if (ReflectUtlis.isAssignableFrom(result.getClass(), Map.class)){
            Integer code = MapUtils.getInteger((Map) result, "code", 0);
            if (code == 201){
                redisCache.put(methodName, result);
            }
        }

        return result;
    }
}
