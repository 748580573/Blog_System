package com.heng.blog_system.db;

import com.heng.blog_system.bean.User;
import com.heng.blog_system.db.impl.PermissionAuth;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


//TODO 完成登陆验证模块
@Component
public class PermissionAuthDAO implements PermissionAuth {

    private static final Logger logger = Logger.getLogger(PermissionAuthDAO.class);

    @Autowired
    BlogDao blogDao;

    @Override
    public User selectUserByAccount(Map<String, Object> param) {
        try {
            User user = blogDao.get("permissionAuth.selectUserByAccountCode",param);
            return user;
        }catch (Exception e){
            logger.info(e);
            e.printStackTrace();
            return null;
        }
    }
}
