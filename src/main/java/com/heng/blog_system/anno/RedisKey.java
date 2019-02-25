package com.heng.blog_system.anno;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisKey {

    int keyTimeOut() default 600;                //key超时，默认为600秒
}
