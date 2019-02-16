package com.heng.blog_system.bean;

import com.heng.blog_system.utils.Utils;

import java.util.Map;

public class Tag {

    private String tagCode;                 //标签编码

    private String tagName;                //标签名

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public static Tag mapToTag(Map<String,Object> map){
        Tag tag = new Tag();
        if (!Utils.isEmpty(map.get("tag_code").toString())){
            tag.setTagCode(map.get("tag_code").toString());
        }
        if (!Utils.isEmpty(map.get("tag_name"))){
            tag.setTagName(map.get("tag_name").toString());
        }
        return tag;

    }
}