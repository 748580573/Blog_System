package com.heng.blog_system.bean;

import com.heng.blog_system.utils.Utils;

import java.io.Serializable;
import java.util.Map;

public class Blog implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    private String blogCode;                             //博客编码

    private String blogTilte;                         //博客标题

    private String blogDesc;                          //博客简介

    private String blogContent;                       //博客内容

    private String blogConverUrl;                    //博客封面Url

    private String tags;                             //博客标签

    private int clickNumber;                        //博客点击量

    private String createDate;                      //博客创建日期

    public String getBlogCode() {
        return blogCode;
    }

    public void setBlogCode(String blogCode) {
        this.blogCode = blogCode;
    }

    public String getBlogTilte() {
        return blogTilte;
    }

    public void setBlogTilte(String blogTilte) {
        this.blogTilte = blogTilte;
    }

    public String getBlogDesc() {
        return blogDesc;
    }

    public void setBlogDesc(String blogDesc) {
        this.blogDesc = blogDesc;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public String getBlogConverUrl() {
        return blogConverUrl;
    }

    public void setBlogConverUrl(String blogConverUrl) {
        this.blogConverUrl = blogConverUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(int clickNumber) {
        this.clickNumber = clickNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * 用于es
     * @param map
     * @return
     */
    public static Blog mapToBlog(Map<String,Object> map){
        Blog blog = new Blog();
        if (!Utils.isEmpty(map.get("blog_code"))){
            blog.blogCode = map.get("blog_code").toString();
        }
        if (!Utils.isEmpty(map.get("blog_title"))){
            blog.blogTilte = map.get("blog_title").toString();
        }
        if (!Utils.isEmpty(map.get("bolg_tags"))){
            blog.tags = map.get("bolg_tags").toString();
        }
        if (!Utils.isEmpty(map.get("blog_desc"))){
            blog.blogDesc = map.get("blog_desc").toString();
        }
        if (!Utils.isEmpty(map.get("imgUrl"))){
            blog.blogConverUrl = map.get("imgUrl").toString();
        }
        if (!Utils.isEmpty(map.get("blog_content"))){
            blog.blogContent = map.get("blog_content").toString();
        }
        return blog;

    }


}
