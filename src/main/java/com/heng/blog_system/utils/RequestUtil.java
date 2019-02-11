package com.heng.blog_system.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class RequestUtil {

    //获取form表单数据
    @SuppressWarnings("unchecked")
    public static Map<String,Object> getFormData(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        Enumeration en = request.getParameterNames();
        while(en.hasMoreElements()){
            String name=(String)en.nextElement();
            String [] values= request.getParameterValues(name);
            if(!name.contains("[]")){
                if(!values[0].equals(""))
                    map.put(name, values[0]);
            }else {
                map.put(name.replace("[]", ""), values);
            }
        }
        // map.put("monthNO",map.get("monthNO").toString().replace("年", ""));
        // map.put("monthNO",map.get("monthNO").toString().replace("月", ""));
        return map;
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 返回文件保存的路径
     * @param request
     * @return
     * @throws IOException
     */
    public static List<String> fileUpLoad(HttpServletRequest request) throws IOException {
        List<String> filePath = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iterator = multiRequest.getFileNames();
            while (iterator.hasNext()){
                MultipartFile file = multiRequest.getFile(iterator.next().toString());
                if (file != null){
                    String path = "e:/" + file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                    filePath.add(path);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("文件上传耗时" + String.valueOf(endTime - startTime));
        return filePath;
    }
}
