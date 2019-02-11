package com.heng.blog_system.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:spring-mvc.xml","classpath:spring-dao.xml"})
public class ContextConfig {
}
