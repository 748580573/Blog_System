package com.heng.blog_system.utils;


import com.sun.management.OperatingSystemMXBean;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class SystemUtils {

    private static Runtime r = Runtime.getRuntime();

    private static Properties props = System.getProperties();

    private static Map<String, String> oparaParam = System.getenv();

    private static SystemInfo si = new SystemInfo();

    private static HardwareAbstractionLayer hal = si.getHardware();

    private static OperatingSystem os = si.getOperatingSystem();

    private static GlobalMemory memory =  hal.getMemory();

    /**
     * 本地IP
     * @return
     */
    public static String localIp(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "暂无信息";
    }

    /**
     * JVM总内存
     * @return
     */
    public static String jvmTotalMemory(){
        return formatDecimal(r.totalMemory() * 1.0 / 1070596096);
    }


    public static String jvmFreeMemory(){
        return formatDecimal(r.freeMemory() * 1.0 / 1070596096);
    }

    public static String jvmRate(){
        return formatDecimal((r.totalMemory() - r.freeMemory()) * 100.0 / r.totalMemory());
    }

    /**
     * JVM启动时间
     * @return
     */
    public static String jvmStartTime(){
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        long startTime = bean.getStartTime();
        Date startDate = new Date(startTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(startDate);
    }

    /**
     * 获取jvm运行时间
     * @return
     */
    public static String jvmRunningTime(){
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        long startTime = bean.getStartTime();
        Date startDate = new Date(startTime);

        Calendar c1 = Calendar.getInstance();   //当前日期
        Calendar c2 = Calendar.getInstance();
        c2.setTime(startDate);   //设置为另一个时间

        int year = c1.get(Calendar.YEAR);
        int oldYear = c2.get(Calendar.YEAR);

        int month = c1.get(Calendar.MONTH);
        int oldMonth = c2.get(Calendar.MONTH);

        int day = c1.get(Calendar.DAY_OF_YEAR);
        int oldDay = c2.get(Calendar.DAY_OF_YEAR);

        int hour = c1.get(Calendar.HOUR_OF_DAY);
        int oldHour = c2.get(Calendar.HOUR_OF_DAY);

        StringBuffer result = new StringBuffer("已运行");

        if (year - oldYear > 0){
            result.append((year - oldDay)  + "年");
        }
        if (month - oldMonth > 0){
            result.append((month - oldMonth) + "月");
        }

        result.append((day - oldDay) + "天");

        result.append((hour - oldHour) + "小时");
        return result.toString();

    }

    public static String jvmVersion(){
        return props.getProperty("java.vm.version");
    }

    public static String operationSystemName(){
        return props.getProperty("os.name") + props.getProperty("os.version");
    }

    public static String totalMemory(){
        return formatDecimal(memory.getTotal() * 1.0 /1070596096);
    }

    public static String availableMemory(){
        return formatDecimal(memory.getAvailable() * 1.0 / 1070596096);
    }

    public static String manufacturer(){
        return hal.getComputerSystem().getManufacturer();
    }

    /**
     * Cpu使用率
     */
    public static String  cpuRate(){
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osmxb.getSystemCpuLoad() * 100;
        return formatDecimal(cpuLoad);
    }

    public static String memoryRate(){
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
        double freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();

        double value = freePhysicalMemorySize/totalvirtualMemory;
        return formatDecimal((1 - value) *100);
    }


    public static String formatDecimal(Object obj){
        return new DecimalFormat("0.00").format(obj);
    }

 }
