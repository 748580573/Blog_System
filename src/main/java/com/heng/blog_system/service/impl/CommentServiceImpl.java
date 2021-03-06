package com.heng.blog_system.service.impl;

import com.heng.blog_system.bean.Comment;
import com.heng.blog_system.bean.Reply;
import com.heng.blog_system.dao.CommentDao;
import com.heng.blog_system.service.CommentService;
import com.heng.blog_system.utils.MapUtils;
import com.heng.blog_system.utils.RequestUtil;
import com.heng.blog_system.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    private Logger logger = Logger.getLogger(CommentServiceImpl.class);

    @Autowired
    CommentDao commentDao;

    @Override
    public Map<String, Object> addComment(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String,Object> form = RequestUtil.getFormData(request);
        Map<String,Object> result = new HashMap<>();

        form.put("userId", -1);
        form.put("userName","游客");
        form.put("time", Utils.obtainCurrentTime());
        try {
            Comment comment = commentDao.addComment(form);
            result.put("data",comment);
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }

    @Override
    public Map<String, Object> addReply(HttpServletRequest request) {
        Map<String,Object> form = RequestUtil.getFormData(request);
        Map<String,Object> result = new HashMap<>();

        form.put("userId", -1);
        form.put("userName","游客");
        form.put("time", Utils.obtainCurrentTime());

        try {
            Reply reply = commentDao.addReply(form);
            result.put("data", reply);
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }
        return result;
    }

    @Override
    public Map<String, Object> selectCommentsById(HttpServletRequest request) {
        Map<String,Object> resulet = new HashMap<>();
        Map<String,Object> form = RequestUtil.getFormData(request);
        try {
            List<Comment> list = commentDao.selectComments(form);
            if (list != null && list.size() > 0){
                for (Comment comment : list){
                    Map<String,Object> param = new HashMap<>();
                    param.put("commentId", comment.getId());
                    List<Reply> replies = commentDao.selectReplys(param);
                    for (Reply reply : replies){
                        reply.setUserName("游客");
                    }
                    comment.setReplies(replies);
                    comment.setUserName("游客");
                }
            }
            resulet.put("data",list);
            MapUtils.setSuccess(resulet);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(resulet);
        }
        return resulet;
    }
}
