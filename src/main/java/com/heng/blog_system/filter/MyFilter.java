package com.heng.blog_system.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @WebFilter将一个是实现了javax.servlet.Filter接口的类定义为过滤器
 * @Order定义过滤器的执行优先级
 */
@WebFilter(filterName = "myfilter",urlPatterns = "/*")
@Order(1)
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
