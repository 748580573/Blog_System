package com.heng.blog_system.db.impl;

import com.heng.blog_system.bean.Permission;
import com.heng.blog_system.bean.Role;
import com.heng.blog_system.bean.User;

import java.util.List;
import java.util.Map;

public interface PermissionAuth {

    /**
     * 通过Account查找用户
     * @param param
     * @return
     */
    public User selectUserByAccount(Map<String,Object> param);

    /**
     *  查新所有角色
     * @param param 查询条件
     * @return
     */
    public List<Role> selectRoles(Map<String,Object> param);

    /**
     * 查询所有权限
     * @param param
     * @return
     */
    public List<Permission> selectPermission(Map<String,Object> param);

    /**
     * 添加用户
     * @param param
     */
    public void addUser(Map<String,Object> param) throws Exception;


    public void addRole(Map<String,Object> param) throws Exception;

    public void addUserRole(Map<String,Object> param) throws Exception;

    public Role selectRoleByRoleCode(Map<String,Object> param);

    public void addRolePermission(Map<String,Object> param) throws Exception;

}
