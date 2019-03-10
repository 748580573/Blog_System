package com.heng.blog_system.service;

import com.heng.blog_system.bean.Visitor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface VisitorService {

    public void addVisitor(Visitor visitor);

    public void addVisitor(HttpServletRequest request);

    public Visitor selectVisitorByFlag(String flag);

    public Map<String,Object> selectVisitorOrderByTime(Map<String,Object> form);
}
