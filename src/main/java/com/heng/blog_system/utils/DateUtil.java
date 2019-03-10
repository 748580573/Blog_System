package com.heng.blog_system.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String obtainCurrentTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 计算date1与date2的时间查
     * @param date1
     * @param date2
     * @return 返回结果单位（minute）
     */
    public static int timeDifference(String date1,String date2){
        try {
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date fromDate3 = simpleFormat.parse(date2);
            Date toDate3 = simpleFormat.parse(date1);
            long from3 = fromDate3.getTime();
            long to3 = toDate3.getTime();
            int minutes = (int) ((to3 - from3) / (1000 * 60));
            return minutes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
