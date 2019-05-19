package com.heng.blog_system.filter;

import com.heng.blog_system.bean.User;
import com.heng.blog_system.service.VisitorService;
import com.heng.blog_system.utils.MapUtils;
import com.heng.blog_system.utils.RegexUtils;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @WebFilter将一个是实现了javax.servlet.Filter接口的类定义为过滤器
 * @Order定义过滤器的执行优先级
 */
@Component
@WebFilter(filterName = "login",urlPatterns = "/*")
@Order(1)
public class LoginFilter implements Filter {

    @Autowired
    VisitorService visitorService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        visitorService.addVisitor(request);                         //记录访问者
        String url = request.getRequestURI();
        Set<String> filter = new HashSet<>();                       //拦截
        Set<String> pass = new HashSet<>();                         //放行
        pass.add("/bac/login\\.html");
        pass.add(".*/img/");
        pass.add(".*/css/");
        pass.add(".*/js/");
        pass.add(".*/fonts/");
        pass.add(".*/error/");

        filter.add("/bac/.*+");

        if (RegexUtils.match(url, pass)){
            filterChain.doFilter(servletRequest,servletResponse );
            return;
        }


        if (RegexUtils.match(url, filter)){
            Map<String,Object> param = RequestUtil.getFormData(request);
            User user = (User) session.getAttribute("user");
            if (user != null){
                String password = MapUtils.getString(param,"password");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }else {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.sendRedirect("/blog_system/bac/login.html");
                return;
            }

        }

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }
}
