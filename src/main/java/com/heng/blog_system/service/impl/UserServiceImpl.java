package com.heng.blog_system.service.impl;

import com.heng.blog_system.bean.User;
import com.heng.blog_system.db.PermissionAuthDAO;
import com.heng.blog_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 该接口不对外开发
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PermissionAuthDAO dao;

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
}
