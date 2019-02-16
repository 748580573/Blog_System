package com.heng.blog_system.bean;

public class Title {
    private int id;

    private int parId;            //父标题Id(最多二级标题)

    private String name;

    private String src;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
