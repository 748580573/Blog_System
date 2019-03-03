package com.heng.blog_system.service.impl;

import com.heng.blog_system.bean.Permission;
import com.heng.blog_system.bean.Role;
import com.heng.blog_system.bean.User;
import com.heng.blog_system.db.impl.PermissionAuth;
import com.heng.blog_system.service.UserService;
import com.heng.blog_system.utils.MapUtils;
import com.heng.blog_system.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private PermissionAuth dao;

    @Override
    public User isExistUser(Map<String, Object> form) {
        User user = dao.selectUserByAccount(form);
        if (user != null){
            String permission = user.getPermission();
            String[] tags = permission.trim().split(",");
        }
        return user;
    }

    @Override
    public boolean hasPermission(User user, String... permissions) {
        Set<String> set = new HashSet<>();         //执行操作需要的权限
        for (String str : permissions){
            set.add(str);
        }
        if (user.getPermission() != null){
            String[] userPermission = user.getPermission().trim().split(",");
            for (String str : userPermission){
                if (set.size() == 0){
                    return true;
                }
                if (set.contains(str)){
                    set.remove(str);
                }
            }
            if (set.size() == 0){
                return true;
            }
        }

        return false;
    }

    @Override
    public Map<String,Object> selectRoles(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        List<Role> list = dao.selectRoles(form);
        if (list != null){
            result.put("code", 201);
            result.put("msg", "查询成功");
            result.put("data", list);
        }else {
            result.put("code", 500);
            result.put("msg", "查询失败！");
        }
        return result;
    }

    public Map<String,Object> selectPermissions(Map<String,Object> form){
        Map<String,Object> result = new HashMap<>();
        List<Permission> list = dao.selectPermission(form);
        if (list != null){
            result.put("code", 201);
            result.put("msg", "查询成功");
            result.put("data", list);
        }else {
            result.put("code", 500);
            result.put("msg", "查询失败！");
        }
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> addUser(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        String roleName = MapUtils.getString(form, "roleName");
        String roleCole = Utils.md5(roleName);
        form.put("roleCode", roleCole);
        User user = dao.selectUserByAccount(form);
        if (user == null){
            try {
                dao.addUser(form);
                if (dao.selectRoleByRoleCode(form) == null){
                    dao.addRole(form);
                }
                dao.addUserRole(form);
                result.put("code", 201);
                result.put("msg", "添加成功");
            } catch (Exception e) {
                logger.info(e);
                e.printStackTrace();
                result.put("code", 500);
                result.put("msg", "服务器内部错误！添加角色失败");
            }
        }else {
            result.put("code", 404);
            result.put("msg", "存在该账号");
        }
        return result;
    }


    @Override
    @Transactional
    public Map<String, Object> addRole(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> param = new HashMap<>();
        String roleName = MapUtils.getString(form, "name");
        String roleCode = Utils.md5(roleName);
        param.put("roleCode", roleCode);
        param.put("roleName", roleName);
        Role role = dao.selectRoleByRoleCode(param);
        try {
            if (role != null){
                result.put("code", 404);
                result.put("msg", "该角色已存在");
            }else {
                dao.addRole(param);
                for(Map.Entry entry : form.entrySet()){
                    if (!"name".equals(entry.getKey())){
                        String key = entry.getKey().toString();
                        param.put("permissionCode", key);
                        dao.addRolePermission(param);
                    }
                }
            }
            result.put("code", 201);
            result.put("msg", "添加成功");
        }catch (Exception e){
            logger.info(e);
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "服务器内部错误，请查看日志");
        }
        return result;
    }
}
