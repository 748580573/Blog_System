package com.heng.blog_system.db.impl;

import com.heng.blog_system.bean.User;

import java.util.Map;

public interface PermissionAuth {

    /**
     * 通过Account查找用户
     * @param param
     * @return
     */
    public User selectUserByAccount(Map<String,Object> param);

}
