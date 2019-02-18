package com.heng.blog_system.test;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.User;
import com.heng.blog_system.utils.Utils;

public class Test {

    public static void main(String[] args) {
        User user = new User();
        user.setPath("123");
        user.setAge(11);
        byte[] bytes = Utils.toByteArray(user);
        Object object = Utils.toObject(bytes);
        System.out.println("lalala");
    }
}
