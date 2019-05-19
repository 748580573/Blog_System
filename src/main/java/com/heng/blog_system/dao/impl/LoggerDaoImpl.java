package com.heng.blog_system.dao.impl;

import com.heng.blog_system.bean.Log;
import com.heng.blog_system.dao.CommonDao;
import com.heng.blog_system.dao.LoggerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class LoggerDaoImpl implements LoggerDao {

    @Autowired
    private CommonDao commonDao;


    @Override
    public int addLogger(Map<String, Object> param) throws Exception {
        return commonDao.save("logger.addLogger", param);
    }

    @Override
    public List<Log> selectLoggerList(Map<String, Object> param) throws Exception {
        return commonDao.getList("logger.selectLogger", param);
    }

    @Override
    public Log selectLogger(Map<String, Object> param) throws Exception {
        return commonDao.get("logger.selectLogger", param);
    }

    @Override
    public int selectTotal(Map<String, Object> param) throws Exception {
        return commonDao.get("logger.selectTotal", param);
    }
}
