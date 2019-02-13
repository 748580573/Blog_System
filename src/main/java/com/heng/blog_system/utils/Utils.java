package com.heng.blog_system.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    private static Logger logger = Logger.getLogger(Utils.class);

    private static ObjectMapper mapper = new ObjectMapper();


    public static String md5(String plainText){
        //定义一个字节数组
        byte[] secreBytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            secreBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String md5code = new BigInteger(1,secreBytes).toString(16);
        for (int i = 0;i < 32 - md5code.length();i++){
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public static boolean isEmpty(Object object){
        return object == null;
    }

    public static String objectToJson(Object object){
        try {
            String json = mapper.writeValueAsString(object);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.info("json数据转换失败:" + object.getClass());
            return null;
        }
    }
}
