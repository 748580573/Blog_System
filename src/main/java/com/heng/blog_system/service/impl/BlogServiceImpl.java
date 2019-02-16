package com.heng.blog_system.service.impl;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.Tag;
import com.heng.blog_system.cache.BlogCache;
import com.heng.blog_system.db.BlogDao;
import com.heng.blog_system.service.BlogService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.TabExpander;
import java.io.IOException;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    private Logger logger = Logger.getLogger(BlogServiceImpl.class);

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
        bl = blogCache.getBlog(blogCode);


        if (Utils.isEmpty(bl)){
            bl = blogDao.get("blogTag.selectBlogForIndex", map);
            if (Utils.isEmpty(bl)){
                bl = Blog.mapToBlog(map);
                String createDate = Utils.obtainCurrentTime();
                map.put("create_date", createDate);
                bl.setCreateDate(createDate);
                blogCache.putBlog(blogCode, bl);

                blogDao.save("blogTag.addBlog", map);
            }
        }
        /*if (Utils.isEmpty()){
            Blog bl = blogDao.get("selectBlog", map);
            if (Utils.isEmpty(bl)){
                bl = Blog.mapToBlog(map);
                blogCache.putBlog(blogCode, bl);
                blogDao.save("blogTag.addBlog", map);
            }
        }*/

        if (map.get("bolg_tags") != null) {
            tags = map.get("bolg_tags").toString();
        }
        String[] bolg_tags = tags.trim().split(" ");

        for (int i = 0; i < bolg_tags.length; i++) {
            if (bolg_tags[i].trim().length() == 0){
                continue;
            }
            Map<String, Object> param = new HashMap<>();
            tag_code = Utils.md5(bolg_tags[i]);
            param.put("tag_code", tag_code);
            param.put("tag_name", bolg_tags[i]);
            Tag tag = blogCache.getTag(tag_code);

            if (Utils.isEmpty(tag)) {
                Tag ta = blogDao.get("blogTag.selectTag",param);
                if (Utils.isEmpty(ta)){
                    blogDao.save("blogTag.addTag", param);
                    blogCache.putTag(tag_code, Tag.mapToTag(param));

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
    public Map<String,Object> selectBlogs(Map<String,Object> form){
        String title = form.get("title").toString();
        Map<String,Object> result = new HashMap<>();
        String indexName = Blog.class.getSimpleName().toLowerCase();
        String indexType = Blog.class.getSimpleName();
        try {
            SearchResponse response = esClient.matchSearch(indexName, indexType, "blogTilte", title);
            SearchHits hits = response.getHits();
            int code = response.status().getStatus();
            long totalHits = hits.getTotalHits();
            result.put("resultTotal", totalHits);
            result.put("code", code);
            List<String> blogCodes = new ArrayList<>();
            for (SearchHit hit : hits){
                blogCodes.add(hit.getSourceAsMap().get("blogCode").toString());
            }

            List<Blog> blogList = new ArrayList<>();
            for (String blogCode : blogCodes){
                Map<String,Object> map = new HashMap<>();
                map.put("blog_code", blogCode);
                Blog blog = blogDao.get("blogTag.selectBlogForSearch",map);
                String tag = blog.getTags().replace(",", " ");
                blog.setTags(tag);
                if (!Utils.isEmpty(blog)){
                    blogList.add(blog);
                }
            }
            result.put("data", blogList);
            return  result;
        } catch (IOException e) {
            logger.info(e);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> selectHosBlogs(Map<String, Object> form) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Blog> blogs = blogDao.getList("blogTag.selectBlogForSearch",form);
            for (Blog blog : blogs){
                if (Utils.isEmpty(blogCache.getBlog(blog.getBlogCode()))){
                    String tag = blog.getTags();
                    blog.setTags(tag.replace(",", " "));
                    blogCache.putBlog(blog.getBlogCode(), blog);
                }
            }
            result.put("data", blogs);
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
    public Map<String, Object> fuzzySearch(Map<String, Object> form,Class<?> clazz) {
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
            SearchResponse response =esClient.boolMulitSearchForShould(indexName, indexType, param);
            SearchHits hits = response.getHits();
            result.put("code", response.status().getStatus());
            result.put("msg", "查询成功");
            List<Blog> list = new ArrayList<>();
            for (SearchHit hit : hits){
                String blogCode = hit.getSourceAsMap().get("blogCode").toString();
                Blog blog = blogCache.getBlog(blogCode);
                if (blog == null){
                    Map<String,Object> tempParam = new HashMap<>();
                    tempParam.put("blog_code", blogCode);
                    blog = blogDao.get("blogTag.selectBlogForIndex",tempParam);
                    blogCache.putBlog(blogCode, blog);
                }
                list.add(blog);

            }
            result.put("data", list);
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
        try {
            List<Blog> list = blogDao.getList("blogTag.selectBlogForRecommend",form);
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
}
