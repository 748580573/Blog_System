package com.heng.blog_system.dao;

import com.heng.blog_system.bean.Tag;

import java.util.Map;

public interface TagDao {

    /**
     * 添加标签
     * @param param
     * @return
     */
    public Tag addTag(Map<String,Object> param);

    public Tag selectTag(Map<String,Object> param);
}
