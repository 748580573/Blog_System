package com.heng.blog_system.service.impl;

import com.heng.blog_system.bean.Visitor;
import com.heng.blog_system.dao.VisitorDao;
import com.heng.blog_system.service.VisitorService;
import com.heng.blog_system.utils.DateUtil;
import com.heng.blog_system.utils.MapUtils;
import com.heng.blog_system.utils.RequestUtil;
import com.heng.blog_system.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VisitorServiceImpl implements VisitorService {

    private Logger logger = Logger.getLogger(VisitorServiceImpl.class);

    @Autowired
    VisitorDao visitorDao;

    @Override
    @Transactional
    public void addVisitor(Visitor visitor) {
        visitorDao.addVisitor(visitor);
    }

    @Override
    @Transactional
    public void addVisitor(HttpServletRequest request) {
        try {
            String ip = RequestUtil.getIpAddr(request);
            String address = RequestUtil.getAddressByIP(ip);
            String flag = Utils.md5(ip);
            String os = RequestUtil.getOS(request);
            String chrome = RequestUtil.getChrome(request);
            String date = Utils.obtainCurrentTime();
            Visitor visitor = new Visitor();
            visitor.setFlag(flag);
            visitor.setAddress(address);
            visitor.setChrome(chrome);
            visitor.setDate(date);
            visitor.setIp(ip);
            visitor.setOs(os);

            Visitor v = visitorDao.selectVisitorByFlag(flag);
            if (v != null){
                if (DateUtil.timeDifference(date,v.getDate()) >= 30){
                    visitorDao.addVisitor(visitor);
                }
            }else {
                visitorDao.addVisitor(visitor);
            }
        }catch (Exception e){
            logger.info(e);
            e.printStackTrace();
        }
    }

    @Override
    public Visitor selectVisitorByFlag(String flag) {
        return null;
    }

    @Override
    public Map<String,Object> selectVisitorOrderByTime(Map<String,Object> form) {
        Map<String,Object> result = new HashMap<>();
        Integer pageNumber = Integer.valueOf(MapUtils.getString(form, "pageNumber","1"));
        Integer pageSize = Integer.valueOf(MapUtils.getString(form, "pageSize","10"));
        Map<String,Object> param = new HashMap<>();
        param.put("pageNumber", pageNumber);
        param.put("pageSize", pageSize);
        param.put("orderKey", "date");
        List<Visitor> list = visitorDao.selectVisitors(param);
        if (list != null){
            int total = visitorDao.visitorTotal();
            int pageTatol = (total + pageSize - 1) / pageSize;
            result.put("pageTatol", pageTatol);
            result.put("pageNumber", pageNumber);
            result.put("code", 200);
            result.put("data", list);
        }else {
            result.put("code",401);
            result.put("data", list);
        }
        return result;
    }
}
