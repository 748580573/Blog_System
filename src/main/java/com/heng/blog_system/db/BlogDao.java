package com.heng.blog_system.db;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 博客系统的数据源
 */
@Component
public class BlogDao extends SqlSessionDaoSupport {

    @Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sessionTemplate) {
        super.setSqlSessionTemplate(sessionTemplate);
    }

    public int save(String key, Object object) {
        return getSqlSession().insert(key, object);
    }
    public int update(String key, Object object){
        return getSqlSession().update(key, object);
    }
    public int delete(String key, Object object) {
        return getSqlSession().delete(key, object);
    }
    public <T> T get(String key, Object params) {
        return (T) getSqlSession().selectOne(key, params);
    }

    public <T> T get(String key){
        return (T)getSqlSession().selectOne(key);
    }
    public <T> List<T> getList(String key) {
        return getSqlSession().selectList(key);
    }
    public <T> List<T> getList(String key, Object params) {
        return getSqlSession().selectList(key, params);
    }

}
