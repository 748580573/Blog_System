package com.heng.blog_system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class RequestUtil {

    private static Logger logger = Logger.getLogger(RequestUtil.class);

    private static String contextPath = RequestUtil.class.getResource("/").getPath();

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
     * 获取操作系统
     * @param request
     * @return
     */
    public static String getOS(HttpServletRequest request){
        String info = request.getHeader("User-Agent");
        String[] strings = info.split("\\)");
        return strings[0].split("\\(")[1];
    }

    public static String getChrome(HttpServletRequest request){
        String info = request.getHeader("User-Agent");
        String[] strings = info.split("\\)");
        return strings[2].split("/")[0];
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
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
     * 获取ips所在地址
     * @param strIP
     * @return
     */
    public static String getAddressByIP(String strIP) {
        try {
            URL url = new URL("http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip="+strIP);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            String ipAddr = result.toString();
            try {
                JSONObject obj1= JSON.parseObject(ipAddr);
                if("0".equals(obj1.get("status").toString())){
                    JSONObject obj2= JSON.parseObject(obj1.get("content").toString());
                    JSONObject obj3= JSON.parseObject(obj2.get("address_detail").toString());
                    return obj3.get("city").toString();
                }else{
                    return "读取失败";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "读取失败";
            }

        } catch (IOException e) {
            return "读取失败";
        }
    }

    /**
     * 返回文件保存的路径
     * @param request
     * @return
     * @throws IOException
     */
    public static List<String> fileUpLoad(HttpServletRequest request,String path) throws IOException {
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
                    //上传
                    String outPath = path + Utils.obtainRandomCode() + file.getOriginalFilename();
                    logger.info("图片上传，路径为" + outPath);
                    Utils.transferTo(file, outPath);
//                    file.transferTo(new File(contextPath + path));
                    filePath.add(outPath.replace(path, "imgs/"));
                }
            }
        }
        long endTime = System.currentTimeMillis();
        if (filePath.size() > 0){
            logger.info("文件上传耗时" + String.valueOf(endTime - startTime));
        }else {
            logger.info("图片上传失败");
        }
        return filePath;
    }

    public static void setResponse(HttpServletResponse response,Map<String,Object> map) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String json = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
        out.append(json);
    }
}
