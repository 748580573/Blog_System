package com.heng.blog_system.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static String contextPath = Utils.class.getResource("").getPath();

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

    /**
     * 获取随机编码
     * @return
     */
    public static String obtainRandomCode(){
        Date date = new Date();
        String time = date.toString();
        String code = md5(time);
        return code;
    }

    public static boolean isEmpty(Object object){
        return object == null;
    }

    /**
     * 获得json数据
     * @param object
     * @return
     */
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

    public static String obtainCurrentTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 文件传输
     * @param file
     * @param outPath
     * @return
     */
    public static boolean transferTo(MultipartFile file, String outPath){
        try {

            InputStream in =file.getInputStream();
            FileOutputStream out = new FileOutputStream(outPath);
            byte[] data = new byte[1204 * 4];
            int length = 0;
            while ((length = in.read(data)) > 0){
                out.write(data);
            }
            in.close();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Integer getDefaultNumber(Object object){
        if (object == null){
            return 0;
        }else {
            String number = object.toString();
            return Integer.parseInt(number);
        }
    }
}
