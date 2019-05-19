package com.heng.blog_system.anno;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AccessLog {

    String funcationname() default "";
}
