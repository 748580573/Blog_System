package com.heng.blog_system.utils;

import java.util.Map;

public class MapUtils {

    public static Integer getInteger(Map map, String key, Integer defalutValue){
        if (map.get(key) !=  null){
            return (Integer) map.get(key);
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
}
