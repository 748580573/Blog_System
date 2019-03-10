package com.heng.blog_system.db;

import com.heng.blog_system.bean.Visitor;

import java.util.List;
import java.util.Map;


public interface VisitorDao {

    public void addVisitor(Visitor visitor);

    public Visitor selectVisitorByFlag(String flag);

    public List<Visitor> selectVisitors(Map<String,Object> param);
}
