package com.heng.blog_system.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private Properties properties;

    private PropertyUtil(){}

    /**
     *
     * @param fileClasspath
     */
    private PropertyUtil(String fileClasspath){
        try {
            properties = new Properties();
            InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream(fileClasspath);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertyUtil getPropertyUtil(String fileClasspath){
        return new PropertyUtil(fileClasspath);
    }

    /**
     * 默认情况下读取类根路径下conf.properties文件
     * @return
     */
    public static PropertyUtil getPropertyUtil(){
        return getPropertyUtil("conf.properties");
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
