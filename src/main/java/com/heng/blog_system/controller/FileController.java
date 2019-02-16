package com.heng.blog_system.controller;

import com.heng.blog_system.utils.RequestUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    private static String system = System.getProperty("os.name");


    @RequestMapping("/fileUpload")
    public Map<String,Object> upLoadFile(HttpServletRequest request) throws IOException {
        Map<String,Object> map = new HashMap<>();
        String path = "/home/F/img/";
        if (system.contains("Windows") || system.contains("windows")) {
            path = "e:/test/";
        }else {
            path = "/home/F/img/";
        }
        String paths = RequestUtil.fileUpLoad(request,path).get(0);
        map.put("path", request.getContextPath() + "/" + paths);
        return map;
    }
}
