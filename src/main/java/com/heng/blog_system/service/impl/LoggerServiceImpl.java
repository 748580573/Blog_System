package com.heng.blog_system.service.impl;

import com.heng.blog_system.bean.Log;
import com.heng.blog_system.dao.LoggerDao;
import com.heng.blog_system.service.LoggerService;
import com.heng.blog_system.utils.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LoggerServiceImpl implements LoggerService {

    private Logger logger = Logger.getLogger(LoggerServiceImpl.class);

    @Autowired
    private LoggerDao loggerDao;

    @Override
    public Map<String, Object> selectLoggerList(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        Integer pageNumber = MapUtils.getInteger(form,"pageNumber" ,1 );
        Integer pageSize = MapUtils.getInteger(form,"pageSize" ,15 );
        form.put("pageNumber", pageNumber);
        form.put("pageSize", pageSize);
        try {
            List<Log> data = loggerDao.selectLoggerList(form);
            int total = loggerDao.selectTotal(form);
            result.put("pageNumber", pageNumber);
            result.put("total", (total + pageSize - 1)/pageSize);
            result.put("data",data );
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }

    @Override
    public Map<String, Object> addLogger(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        try {
            loggerDao.addLogger(form);
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }

    @Override
    public Map<String, Object> selectLogger(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();

        try {
            Log log = loggerDao.selectLogger(form);
            result.put("data",log );
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            MapUtils.setFail(result);
        }
        return result;
    }
}
