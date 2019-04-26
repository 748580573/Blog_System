package com.heng.blog_system.dao.impl;

import com.heng.blog_system.bean.Comment;
import com.heng.blog_system.bean.Reply;
import com.heng.blog_system.dao.CommentDao;
import com.heng.blog_system.dao.CommonDao;
import com.heng.blog_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private CommonDao commonDao;

    @Override
    public Comment addComment(Map<String, Object> param)  throws Exception{
        String json = Utils.objectToJson(param);
        Comment comment = Utils.jsonToObject(json,Comment.class );
        commonDao.save("comment.addComment", comment);
        return comment;
    }

    @Override
    public Reply addReply(Map<String, Object> param) throws Exception {
        String json = Utils.objectToJson(param);
        Reply reply =Utils.jsonToObject(json,Reply.class);
        commonDao.save("comment.addReply", reply);
        return reply;
    }
}
