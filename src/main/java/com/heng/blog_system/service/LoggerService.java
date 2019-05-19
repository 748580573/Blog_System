package com.heng.blog_system.service;

import java.util.Map;

public interface LoggerService {

    Map<String,Object> selectLoggerList(Map<String,Object> form);

    Map<String,Object> selectLogger(Map<String,Object> form);

    Map<String,Object> addLogger(Map<String,Object> form);
}
