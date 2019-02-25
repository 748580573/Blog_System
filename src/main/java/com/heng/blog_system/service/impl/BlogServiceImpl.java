package com.heng.blog_system.service.impl;

import com.heng.blog_system.anno.RedisKey;
import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.Tag;
import com.heng.blog_system.cache.BlogCache;
import com.heng.blog_system.db.BlogDao;
import com.heng.blog_system.db.RedisCache;
import com.heng.blog_system.service.BlogService;
import com.heng.blog_system.utils.MapUtils;
import com.heng.blog_system.utils.Utils;
import com.heng.util.ESClient;
import com.heng.util.ESUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.TabExpander;
import java.io.IOException;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    private Logger logger = Logger.getLogger(BlogServiceImpl.class);

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private BlogCache blogCache;

    @Autowired
    private ESClient esClient;

    @Autowired
    private BlogDao blogDao;

    @Override
    @Transactional
    public Map<String, Object> addBlog(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        String blogCode = null;
        String tag_code = null;
        String tags = "";
        Blog blog = null;



        if (map.get("blog_title") != null) {
            blogCode = Utils.md5(map.get("blog_title").toString());
            map.put("blog_code", blogCode);
        }

        Blog bl = null;
//        bl = blogCache.getBlog(blogCode);

        bl = (Blog) redisCache.get(blogCode);


        if (Utils.isEmpty(bl)){
            bl = blogDao.get("blogTag.selectBlogForIndex", map);
            if (Utils.isEmpty(bl)){
                bl = Blog.mapToBlog(map);
                String createDate = Utils.obtainCurrentTime();
                map.put("create_date", createDate);
                bl.setCreateDate(createDate);
                redisCache.put(blogCode, bl);
//                blogCache.putBlog(blogCode, bl);
                blogDao.save("blogTag.addBlog", map);
            }
        }

        if (map.get("bolg_tags") != null) {
            tags = map.get("bolg_tags").toString();
        }
        String[] bolg_tags = getTags(tags);

        for (int i = 0; i < bolg_tags.length; i++) {
            if (bolg_tags[i].trim().length() == 0){
                continue;
            }
            Map<String, Object> param = new HashMap<>();
            tag_code = Utils.md5(bolg_tags[i]);
            param.put("tag_code", tag_code);
            param.put("tag_name", bolg_tags[i]);
            Tag tag = (Tag) redisCache.get(tag_code);

            if (Utils.isEmpty(tag)) {
                Tag ta = blogDao.get("blogTag.selectTag",param);
                if (Utils.isEmpty(ta)){
                    blogDao.save("blogTag.addTag", param);
                    redisCache.put(tag_code, Tag.mapToTag(param));

                }
            }
            Map<String,Object> tagBlog = new HashMap<>();                 //关联tag与blog
            tagBlog.put("blog_code", blogCode);
            tagBlog.put("tag_code", tag_code);
            blogDao.save("blogTag.insertTagBlog", tagBlog);
        }



        //存入ES（el中不存博客内容）
        blog = Blog.mapToBlog(map);
        blog.setBlogContent("");


        try {
            String indexName = ESUtils.getEsIndexNameFromClazz(Blog.class);
            String type = ESUtils.getEsTypeFromClazz(Blog.class);
            Class<?> clazz = blog.getClass();
            if (!esClient.existIndex(indexName)) {
                esClient.createIndex(indexName, type, clazz);
                IndexResponse response = esClient.insertData(indexName, type, blog);
                RestStatus status = response.status();
                result.put("code", status.getStatus());
                logger.info("向es插入数据" + Utils.objectToJson(blog));
            }else {
                if(!Utils.isEmpty(blog)) {
                    long hitNum = esClient.preciseSearch(indexName, type, "blogCode",blogCode).getHits().totalHits;
                    if (hitNum == 0) {
                        IndexResponse response = esClient.insertData(indexName, type, blog);
                        RestStatus status = response.status();
                        result.put("code", status.getStatus());
                        logger.info("向es插入数据" + Utils.objectToJson(blog));
                    } else {
                        logger.info("es已存在该数据" + Utils.objectToJson(blog));
                    }
                }
            }
            result.put("code", 201);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("error", "插入数据失败");
        return result;
    }


    @Transactional
    public Map<String,Object> selectBlogList(Map<String,Object> form){
        Integer page = Utils.getDefaultNumber(form.get("pageNumber"), 1);
        Integer pageSize = Utils.getDefaultNumber(form.get("pageTotal"), 6);
        form.put("pageNumber", page);
        String redisKey = "listBlog" + page;
        Map<String,Object> result = new HashMap<>();
        List<Blog> data = (List<Blog>) redisCache.get(redisKey);
        try {
            if (data == null || data.size() <= 0){
                data = blogDao.getList("blogTag.selectBlogForSearch",form);
                redisCache.put(redisKey, data);
            }
            int total = blogDao.get("blogTag.selectBlogTotal");
            result.put("total", (total + pageSize) / pageSize);
            result.put("data", data);
            result.put("data", 201);
            return  result;
        } catch (Exception e) {
            result.put("message", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> selectHostBlogs(Map<String, Object> form) {
        Map<String, Object> result = new HashMap<>();

        List<Blog> data = (List<Blog>) redisCache.get("host");

        try {
            if (data == null || data.size() <= 0){
                data = blogDao.getList("blogTag.selectBlogForSearch",form);
                redisCache.put("host", data);
            }
            for (Blog blog : data){
//                if (Utils.isEmpty(blogCache.getBlog(blog.getBlogCode()))){
                if (Utils.isEmpty(redisCache.get(blog.getBlogCode()))){
                    String tag = blog.getTags();
                    blog.setTags(tag.replace(",", " "));
                    redisCache.put(blog.getBlogCode(), blog);
//                    blogCache.putBlog(blog.getBlogCode(), blog);
                }
            }
            result.put("data", data);
            result.put("code", 201);
            result.put("message", "查询成功");
            return result;
        }catch (Exception e){
            logger.info(e);
            result.put("code", "401");
            result.put("message", e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> fuzzySearch(Map<String, Object> form,Class<?> clazz) {
        Integer pageNo = Utils.getDefaultNumber(form.get("pageNo"),1);
        Integer pageSize = Utils.getDefaultNumber(form.get("pageSize"), 3);
        String key = null;
        String indexName = ESUtils.getEsIndexNameFromClazz(clazz);
        String indexType = ESUtils.getEsTypeFromClazz(clazz);
        if (form.get("key") != null){
            key = form.get("key").toString();
        }
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> param = new HashMap<>();
        param.put("blogTilte",key );
        param.put("tags", key);
        try {
            SearchResponse response =esClient.boolMulitSearchForShould(indexName, indexType, param,pageNo,pageSize);
            logger.info("站内搜索到的数据：" + response);
            SearchHits hits = response.getHits();
            result.put("total", (hits.totalHits + pageSize) / pageSize);
            result.put("code", response.status().getStatus());
            result.put("msg", "查询成功");
            List<Blog> list = new ArrayList<>();
            for (SearchHit hit : hits){
                String blogCode = hit.getSourceAsMap().get("blogCode").toString();
//                Blog blog = blogCache.getBlog(blogCode);
                Blog blog = (Blog) redisCache.get(blogCode);
                if (blog == null){
                    Map<String,Object> tempParam = new HashMap<>();
                    tempParam.put("blog_code", blogCode);
                    blog = blogDao.get("blogTag.selectBlogForIndex",tempParam);
//                    blogCache.putBlog(blogCode, blog);
                    redisCache.put(blogCode, blog);
                }
                list.add(blog);

            }
            result.put("data", list);
            result.put("code", 201);
            return result;
        } catch (IOException e) {
            logger.info(e);
            result.put("code", "401");
            result.put("msg", "es查询异常");
        }

        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> selectRecommendBlog(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        form.put("pageNumber", 1);
        form.put("pageTotal", 6);
        List<Blog> list = (List<Blog>) redisCache.get("recommend");
        try {
            if (list == null || list.size() <= 0){
                list = blogDao.getList("blogTag.selectBlogForRecommend",form);
                redisCache.put("recommend", list);
            }
            result.put("data", list);
            result.put("code", 201);
            result.put("msg", "查询成功");
        }catch (Exception e){
            logger.info(e);
            result.put("code", 500);
            result.put("msg", "查询数据失败,请查看日志");
        }
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> selectBlog(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        if (!Utils.isEmpty(form.get("blog_code"))){
            String blogCode = form.get("blog_code").toString();
            try {
                Blog blog = blogDao.get("blogTag.selectBlogForShow",form);
                result.put("code",201 );
                result.put("msg", "查询成功");
                result.put("data", blog);
                return result;
            }catch (Exception e){
                logger.info("查询:" +  blogCode + " 博客失败");
                result.put("code",500 );
                result.put("msg", "查询失败");
                logger.info(e);
            }
        }
        result.put("code",500);
        result.put("msg", "缺少参数");
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> selectBlogForRank(Map<String, Object> form) {
        List<Blog> data = (List<Blog>) redisCache.get("rank");
        Map<String,Object> result = new HashMap<>();
        try {
            if (data == null || data.size() <= 0){
                data = blogDao.getList("blogTag.selectBlogForRank",form);
                redisCache.put("rank", data);
            }
            result.put("code", 201);
            result.put("data", data);
            result.put("msg", "查询成功");
        }catch (Exception e){
            result.put("code", 500);
            result.put("msg", "查询数据异常，请检查日志");
            e.printStackTrace();
            logger.info(e);
        }
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> selectNewBlogs(Map<String, Object> form) {
        Map<String, Object> result = new HashMap<>();

        List<Blog> data = (List<Blog>) redisCache.get("new");

        try {
            if (data == null || data.size() <= 0){
                data = blogDao.getList("blogTag.selectBlogForSearch",form);
                redisCache.put("new", data);
            }
            for (Blog blog : data){
//                if (Utils.isEmpty(blogCache.getBlog(blog.getBlogCode()))){
                if (Utils.isEmpty(redisCache.get(blog.getBlogCode()))){
                    String tag = blog.getTags();
                    blog.setTags(tag.replace(",", " "));
                    redisCache.put(blog.getBlogCode(), blog);
//                    blogCach
// jjjj
// e.putBlog(blog.getBlogCode(), blog);
                }
            }
            result.put("data", data);
            result.put("code", "201");
            result.put("message", "查询成功");
            return result;
        }catch (Exception e){
            logger.info(e);
            result.put("code", "401");
            result.put("message", e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> updateBlog(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        String blog_title= MapUtils.getString(form, "blog_title");
        String blog_code = Utils.md5(blog_title);
        form.put("blog_code", blog_code);
        String newTag = MapUtils.getString(form, "bolg_tags");

        if (blog_code != null){
            try {
                blogDao.update("blogTag.updateBlog", form);               //更新blog
                String oldTag = blogDao.get("blogTag.selecTagsByBlog",form);
                Set<String> newTags = compareTags(getTags(newTag), getTags(oldTag));
                for (String tag : newTags){
                    Map<String, Object> param = new HashMap<>();
                    param.put("blog_code", blog_code);
                    String tag_code = Utils.md5(tag);
                    param.put("tag_code", tag_code);
                    param.put("tag_name", tag);
                    Tag t = blogDao.get("blogTag.selectTag", param);
                    if (t == null){
                        blogDao.save("blogTag.addTag", param);
                    }
                    blogDao.save("blogTag.insertTagBlog", param);
                }
                result.put("code", "201");
                result.put("msg", "亲！修改成功。");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                logger.info(e);
                result.put("code", "501");
                result.put("msg", "修改失败，服务器内部错误，请查看日志");
            }
        }else {
            result.put("code", "404");
            result.put("msg", "修改失败，未找到该博客!");
        }

        return result;
    }

    @RedisKey(keyTimeOut = 60)
    public Map<String,Object> test(){
        Map<String,Object> result = new HashMap<>();
        result.put("code", 201);
        return result;
    }

    private String[] getTags(String str){
        String[] tags = str.trim().split("\\s+");
        return tags;
    }

    private Set<String> compareTags(String[] newTags,String[] oldTags){
        boolean flag = false;

        Set<String> set = new HashSet<>();
        Set<String> newSet = new HashSet<>();
        for (int i = 0;i < oldTags.length;i++){
            if (!set.contains(oldTags[i])){
                set.add(oldTags[i]);
            }
        }
        for (int i = 0;i < newTags.length;i++){
            if (!set.contains(newTags[i])){
                newSet.add(newTags[i]);
            }
        }
        return newSet;
    }
}
