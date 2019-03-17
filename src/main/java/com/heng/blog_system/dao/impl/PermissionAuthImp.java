package com.heng.blog_system.dao.impl;

import com.heng.blog_system.bean.Permission;
import com.heng.blog_system.bean.Role;
import com.heng.blog_system.bean.User;
import com.heng.blog_system.dao.CommonDao;
import com.heng.blog_system.dao.PermissionAuth;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


//TODO 完成登陆验证模块
@Component
public class PermissionAuthImp implements PermissionAuth {

    private static final Logger logger = Logger.getLogger(PermissionAuthImp.class);

    @Autowired
    CommonDao commonDao;

    @Override
    public User selectUserByAccount(Map<String, Object> param) {
        try {
            return commonDao.get("permissionAuth.selectUserByAccountCode",param);
        }catch (Exception e){
            logger.info(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Role> selectRoles(Map<String, Object> param) {
        try {
            List<Role> list = commonDao.getList("permissionAuth.selectRoles", param);
            return list;
        }catch (Exception e){
            logger.info(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Permission> selectPermission(Map<String, Object> param) {
        try {
            List<Permission> list = commonDao.getList("permissionAuth.selectPermissions",param);
            return list;
        }catch (Exception e){
            logger.info(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override

    public void addUser(Map<String, Object> param) throws Exception{
        commonDao.save("permissionAuth.addUser", param);
    }

    @Override
    public void addRole(Map<String, Object> param) throws Exception {
        commonDao.save("permissionAuth.addRole", param);
    }

    @Override
    public void addUserRole(Map<String, Object> param) throws Exception {
        commonDao.save("permissionAuth.addUserRole", param);
    }

    @Override
    public Role selectRoleByRoleCode(Map<String, Object> param) {
        try {
            Role role = commonDao.get("permissionAuth.selectRoleByRoleCode",param);
            return role;
        }catch (Exception e){
            logger.info(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addRolePermission(Map<String, Object> param) throws Exception {
        commonDao.save("permissionAuth.addRolePermission", param);
    }
}
