package com.heng.blog_system.aspect;

import com.heng.blog_system.anno.Permission;
import com.heng.blog_system.bean.User;
import com.heng.blog_system.service.impl.UserServiceImpl;
import com.heng.blog_system.utils.ReflectUtlis;
import org.apache.http.HttpStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Order(1)
public class PermissionAspect {

    @Autowired
    private UserServiceImpl userService;

    @Pointcut("execution(* com.heng.blog_system.service.impl..*.*(..))")
    public void controller(){}

    @Around("controller()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        boolean auth = ReflectUtlis.methodHasAnnotation(targetMethod, Permission.class);
        //是否需要权限验证
        if (auth){
            Permission permission = targetMethod.getAnnotation(Permission.class);
            Map<String,Object> form = (Map<String, Object>) jp.getArgs()[0];
            String[] values = permission.value();
            HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null){
                if (!userService.hasPermission(user, values)){
                    Map<String,Object> result = new HashMap<>();
                    result.put("code", HttpStatus.SC_UNAUTHORIZED);
                    result.put("msg", "sorry,您需要有该权限才能访问");
                    return result;
                }
            }
        }

        return jp.proceed();

    }
}
