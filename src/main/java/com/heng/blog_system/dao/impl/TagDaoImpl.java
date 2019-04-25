package com.heng.blog_system.dao.impl;

import com.heng.blog_system.bean.Tag;
import com.heng.blog_system.dao.CommonDao;
import com.heng.blog_system.dao.TagDao;
import com.heng.blog_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TagDaoImpl implements TagDao {

    @Autowired
    private CommonDao commonDao;

    @Override
    public Tag addTag(Map<String, Object> param) {
        String json = Utils.objectToJson(param);
        Tag tag = Utils.jsonToObject(json, Tag.class);
        commonDao.save("blogTag.addTag", tag);
        return tag;
    }

    @Override
    public Tag selectTag(Map<String, Object> param) {
        return commonDao.get("blogTag.selectTag", param);
    }
}
