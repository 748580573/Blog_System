package com.heng.blog_system.controller;


import com.heng.blog_system.bean.User;
import com.heng.blog_system.service.UserService;
import com.heng.blog_system.utils.MapUtils;
import com.heng.blog_system.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login.html")
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String context = request.getContextPath();
        Map<String,Object> form = RequestUtil.getFormData(request);
        Map<String,Object> model = new HashMap<>();
        User user = userService.isExistUser(form);
        if (user != null){
            String password = MapUtils.getString(form, "password");
            if (password.equals(user.getPassword())){
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                model.put("code", 201);
                model.put("msg", "登陆成功");
            }else {
                model.put("code", 401);
                model.put("msg", "密码错误");
            }
        }
        return model;
    }
}
