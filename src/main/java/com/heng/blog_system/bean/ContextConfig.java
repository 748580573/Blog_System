package com.heng.blog_system.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:spring-mvc.xml"})
public class ContextConfig {
}
