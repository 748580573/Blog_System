package com.heng.blog_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class BlogSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSystemApplication.class, args);
    }
}

