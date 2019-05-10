package com.heng.blog_system.service.impl;

import com.heng.blog_system.service.InfoIService;
import com.heng.blog_system.utils.SystemUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoIService {

    @Override
    public Map<String, Object> getSystemInfo() {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> cpu = new HashMap<>();
        Map<String,Object> memory = new HashMap<>();
        Map<String,Object> server = new HashMap<>();
        Map<String,Object> jvm = new HashMap<>();

        server.put("系统名", SystemUtils.operationSystemName());
        server.put("总内存", SystemUtils.totalMemory() + " G");
        server.put("可使用内存", SystemUtils.availableMemory() + " G");
        server.put("生产商", SystemUtils.manufacturer());
        server.put("cpu使用率", SystemUtils.cpuRate() + " %");
        server.put("内存使用率", SystemUtils.memoryRate() + " %");

        jvm.put("总内存", SystemUtils.jvmTotalMemory() + " G");
        jvm.put("JVM启动时间", SystemUtils.jvmStartTime());
        jvm.put("JVM运行时间", SystemUtils.jvmRunningTime());
        jvm.put("剩余内存", SystemUtils.jvmFreeMemory() + " G");


        result.put("server", server);
        result.put("jvm", jvm);
        return result;
    }

    @Override
    public Map<String, Object> getSystemRate() {
        Map<String,Object> result = new HashMap<>();
        result.put("cupRate", SystemUtils.cpuRate());
        result.put("memoryRate", SystemUtils.memoryRate());
        result.put("jvmRate", SystemUtils.jvmRate());
        return result;
    }
}
