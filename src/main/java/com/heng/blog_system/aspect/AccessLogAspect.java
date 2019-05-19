package com.heng.blog_system.aspect;

import com.heng.blog_system.anno.AccessLog;
import com.heng.blog_system.dao.LoggerDao;
import com.heng.blog_system.service.LoggerService;
import com.heng.blog_system.utils.RequestUtil;
import com.heng.blog_system.utils.Utils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AccessLogAspect {

    private Logger logger = Logger.getLogger(AccessLogAspect.class);

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.heng.blog_system.anno.AccessLog)")
    public void controller(){}

    @Around("controller()")
    public Object arround(ProceedingJoinPoint jp) throws Throwable {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        AccessLog accessLog = targetMethod.getAnnotation(AccessLog.class);
        String methodName = targetMethod.toString();
        String funcationName = accessLog.funcationname();             //功能名
        String ip = RequestUtil.getIpAddr(request);
        Map<String,Object> loggerBean = new HashMap<>();
        String time = Utils.obtainCurrentTime();
        loggerBean.put("address", RequestUtil.getAddressByIP(ip));
        loggerBean.put("ip",ip);
        loggerBean.put("desc", funcationName);
        loggerBean.put("method", methodName);
        loggerBean.put("time", time);
        loggerBean.put("type",1 );

        try {
            loggerService.addLogger(loggerBean);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
        }
        return jp.proceed();
    }

}
