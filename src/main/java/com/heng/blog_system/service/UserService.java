package com.heng.blog_system.service;

import com.heng.blog_system.bean.User;

import java.util.Map;

public interface UserService {

    /**
     * 是否存在该用户，存在该用户则返回User,不存在则返回null
     * @param form
     * @return
     */
    public User isExistUser(Map<String,Object> form);

    /**
     * 判断用户是否存在该权限
     * @param user
     * @param permissions
     * @return
     */
    public boolean hasPermission(User user,String... permissions);
}
