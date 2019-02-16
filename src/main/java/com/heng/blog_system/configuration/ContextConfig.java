package com.heng.blog_system.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ImportResource(locations = {"classpath:spring-mvc.xml","classpath:spring-dao.xml"})
public class ContextConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String system  = System.getProperty("os.name");
        if (system.contains("Windows") || system.contains("windows")){
            registry.addResourceHandler("/imgs/**").addResourceLocations("file:E:/test/");
        }else {
            registry.addResourceHandler("/imgs/**").addResourceLocations("file:/home/F/img/");
        }

        super.addResourceHandlers(registry);
    }
}
