package com.heng.blog_system.utils;

import java.util.Map;

public class MapUtils {

    public static Integer getInteger(Map map, String key, Integer defalutValue){
        if (map.get(key) !=  null){
            return Integer.parseInt(map.get(key).toString().trim());
        }else {
            return defalutValue;
        }
    }

    public static String getString(Map map, String key){
        if (map.get(key) !=  null){
            return (String) map.get(key);
        }else {
            return null;
        }
    }

    public static String getString(Map map, String key,String defaultValue){
        if (map.get(key) !=  null){
            return (String) map.get(key);
        }else {
            return defaultValue;
        }
    }

    public static void setSuccess(Map map){
        map.put("msg", "操作成功");
        map.put("code",200);
    }

    public static void setFail(Map map){
        map.put("msg","服务器内部错误，请联系管理员!");
        map.put("code", 500);
    }
}
