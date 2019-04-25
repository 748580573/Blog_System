package com.heng.blog_system.bean;

import com.heng.blog_system.utils.Utils;

import java.io.Serializable;
import java.util.Map;

public class Tag implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;


    private Integer tagCode;                 //标签编码

    private String tagName;                //标签名

    public Integer getTagCode() {
        return tagCode;
    }

    public void setTagCode(Integer tagCode) {
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
            tag.setTagCode(Integer.valueOf(map.get("tag_code").toString()));
        }
        if (!Utils.isEmpty(map.get("tag_name"))){
            tag.setTagName(map.get("tag_name").toString());
        }
        return tag;

    }
}
