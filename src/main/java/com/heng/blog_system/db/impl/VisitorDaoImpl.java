package com.heng.blog_system.db.impl;

import com.heng.blog_system.bean.Visitor;
import com.heng.blog_system.db.CommonDao;
import com.heng.blog_system.db.VisitorDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VisitorDaoImpl implements VisitorDao {

    private Logger logger = Logger.getLogger(VisitorDaoImpl.class);

    @Autowired
    CommonDao commonDao;

    @Override
    @Transactional
    public void addVisitor(Visitor visitor) {
        try {
            commonDao.save("visitor.addVisitor", visitor);
        }catch (Exception e){
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Visitor selectVisitorByFlag(String flag) {
        Map<String,Object> param = new HashMap<>();
        param.put("flag", flag);
        try {
            return commonDao.get("visitor.selectVisitorByFlag",param);
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e);
            return null;
        }
    }

    @Override
    public List<Visitor> selectVisitors(Map<String, Object> param) {
        List<Visitor> list = null;
        try {
            list = commonDao.getList("visitor.selectVisitors",param);
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e);
        }
        return list;
    }
}
