package com.heng.blog_system.controller;

import com.heng.blog_system.service.UserService;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/roles")
    public Map<String,Object> selectRoles(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        Map<String,Object> result = userService.selectRoles(form);
        return result;
    }

    @RequestMapping(value = "/permissions")
    public Map<String,Object> selectPermissions(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        Map<String,Object> result = userService.selectPermissions(form);
        return result;
    }

    @RequestMapping(value = "/addUser")
    public Map<String,Object> addUser(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        Map<String,Object> result = userService.addUser(form);
        return result;
    }

    //TODO 完成添加角色功能
    @RequestMapping(value = "/addRole")
    public Map<String,Object> addRole(HttpServletRequest request){
        Map<String,Object> form = RequestUtil.getFormData(request);
        Map<String,Object> result = userService.addRole(form);
        return form;
    }
}
