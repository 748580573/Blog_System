package com.heng.blog_system.bean;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    private String name;

    private int age;

    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
