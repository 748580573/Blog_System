package com.heng.blog_system.bean;

import java.io.Serializable;

public class Title implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

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
