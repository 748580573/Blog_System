package com.heng.blog_system.dao;

import com.heng.blog_system.bean.Log;

import java.util.List;
import java.util.Map;

public interface LoggerDao {

    /**
     * 记录日志
     * @param param
     * @return
     * @throws Exception
     */
    int addLogger(Map<String,Object> param)throws Exception;

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<Log> selectLoggerList(Map<String,Object> param) throws Exception;

    Log selectLogger(Map<String,Object> param) throws Exception;

    int selectTotal(Map<String,Object> param) throws  Exception;
}
