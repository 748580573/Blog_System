package com.heng.blog_system.service.impl;

import com.heng.blog_system.anno.Permission;
import com.heng.blog_system.anno.PermissionEnum;
import com.heng.blog_system.anno.RedisKey;
import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.Tag;
import com.heng.blog_system.dao.BlogDao;
import com.heng.blog_system.dao.CommonDao;
import com.heng.blog_system.dao.RedisCache;
import com.heng.blog_system.dao.ESService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    private Logger logger = Logger.getLogger(BlogServiceImpl.class);

    @Autowired
    private RedisCache redisCache;


    @Autowired
    private ESClient esClient;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ESService esService;

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

        Blog bl = commonDao.get("blogTag.selectBlogForIndex", map);
        if (Utils.isEmpty(bl)){
            bl = Blog.mapToBlog(map);
            String createDate = Utils.obtainCurrentTime();
            map.put("create_date", createDate);
            bl.setCreateDate(createDate);
            commonDao.save("blogTag.addBlog", map);
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
                Tag ta = commonDao.get("blogTag.selectTag",param);
                if (Utils.isEmpty(ta)){
                    commonDao.save("blogTag.addTag", param);
                    redisCache.put(tag_code, Tag.mapToTag(param));

                }
            }
            Map<String,Object> tagBlog = new HashMap<>();                 //关联tag与blog
            tagBlog.put("blog_code", blogCode);
            tagBlog.put("tag_code", tag_code);
            commonDao.save("blogTag.insertTagBlog", tagBlog);
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
//    @RedisKey
    public Map<String,Object> selectBlogList(Map<String,Object> form){
        Integer pageNumber = MapUtils.getInteger(form,"pageNumber" , 1);
        Integer pageSize = MapUtils.getInteger(form,"pageSize" , 10);
        form.put("pageNumber", pageNumber);
        form.put("pageSize", pageSize);
        String redisKey = "listBlog" + pageNumber;
        Map<String,Object> result = new HashMap<>();
        List<Blog> data = commonDao.getList("blogTag.selectBlogForSearch",form);
        try {
            int total = commonDao.get("blogTag.selectBlogTotal");
            result.put("total", (total + pageSize - 1) / pageSize);
            result.put("pageNumber", pageNumber);
            result.put("data", data);
            result.put("code", 201);
            return  result;
        } catch (Exception e) {
            result.put("message", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> selevtBlogsOrderByTime(Map<String, Object> form) {
        Integer page = Utils.getDefaultNumber(form.get("pageNumber"), 1);
        Integer pageSize = Utils.getDefaultNumber(form.get("pageSize"), 6);
        form.put("orderKey", "create_date");
        form.put("pageNumber", page);
        String redisKey = "listBlog" + page;
        Map<String,Object> result = new HashMap<>();
        List<Blog> data = (List<Blog>) redisCache.get(redisKey);
        try {
            if (data == null || data.size() <= 0){
                data = commonDao.getList("blogTag.selectBlogForSearch",form);
                redisCache.put(redisKey, data);
            }
            int total = commonDao.get("blogTag.selectBlogTotal");
            result.put("total", (total + pageSize - 1) / pageSize);
            result.put("data", data);
            result.put("code", 201);
            return  result;
        } catch (Exception e) {
            result.put("message", e.getMessage());
            e.printStackTrace();
        }
        return result;

    }

    @Override
    @Transactional
    @RedisKey
    public Map<String, Object> selectHostBlogs(Map<String, Object> form) {
        Map<String, Object> result = new HashMap<>();

        List<Blog> data = (List<Blog>) redisCache.get("host");

        try {
            if (data == null || data.size() <= 0){
                data = commonDao.getList("blogTag.selectBlogForSearch",form);
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
        Integer pageNumber = Integer.valueOf(MapUtils.getString(form,"pageNumber","1"));
        Integer pageSize = Integer.valueOf(MapUtils.getString(form,"pageSize","9"));
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
            SearchResponse response =esClient.boolMulitSearchForShould(indexName, indexType, param,pageNumber,pageSize);
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
                    blog = commonDao.get("blogTag.selectBlogForIndex",tempParam);
//                    blogCache.putBlog(blogCode, blog);
                    redisCache.put(blogCode, blog);
                }
                list.add(blog);

            }
            response = esClient.boolMulitSearchForShould(indexName, indexType, param);
            int total = (int) response.getHits().totalHits;
            result.put("pageTotal", (total + pageSize - 1) / pageSize);
            result.put("data", list);
            result.put("code", 201);
            result.put("pageNumber",pageNumber);
            return result;
        } catch (IOException e) {
            logger.info(e);
            result.put("code", "401");
            result.put("msg", "es查询异常");
        }

        return result;
    }

    @Override
    public Map<String, Object> fuzzySearchByDB(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        Integer pageNumber = MapUtils.getInteger(form,"pageNumber" , 1);
        Integer pageSize = MapUtils.getInteger(form,"pageSize" , 10);
        form.put("pageNumber", pageNumber);
        form.put("pageSize", pageSize);

        try {
            List<Blog> data = blogDao.selectBlogForDBFuzzy(form);
            int total = blogDao.selectBlogTotal(form);
            result.put("data",data);
            result.put("pageNumber",pageNumber);
            result.put("total",(total + pageSize - 1)/pageSize );
            MapUtils.setSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            MapUtils.setFail(result);
        }

        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> selectRecommendBlog(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        Integer pageNumber = Integer.valueOf(MapUtils.getString(form, "pageNumber","1"));
        Integer pageSize = Integer.valueOf(MapUtils.getString(form, "pageSize","9"));
        form.put("pageNumber", pageNumber);
        form.put("pageSize", pageSize);
        List<Blog> list = (List<Blog>) redisCache.get("recommend" + pageNumber);
        try {
            if (list == null || list.size() <= 0){
                list = commonDao.getList("blogTag.selectBlogForRecommend",form);
                redisCache.put("recommend" + pageNumber, list);
            }
            int pageTotal = commonDao.get("blogTag.selectBlogTotal");
            result.put("pageNumber", pageNumber);
            result.put("pageTotal", (pageTotal + pageSize - 1) / pageSize);
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
                Blog blog = commonDao.get("blogTag.selectBlogForShow",form);
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
    @RedisKey
    public Map<String, Object> selectBlogForRank(Map<String, Object> form) {
        Integer pageNumber = Integer.valueOf(MapUtils.getString(form,"pageNumber","1"));
        Integer pageSize = Integer.valueOf(MapUtils.getString(form,"pageSize","9"));
        form.put("pageNumber", pageNumber);
        form.put("pageSize", pageSize);
        Map<String,Object> result = new HashMap<>();
        try {
            List<Blog> data = commonDao.getList("blogTag.selectBlogForRank",form);
            redisCache.put("rank", data);
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
        try {
            List<Blog> data = commonDao.getList("blogTag.selectBlogForSearch",form);
            redisCache.put("new", data);
            for (Blog blog : data){
                if (Utils.isEmpty(redisCache.get(blog.getBlogCode()))){
                    String tag = blog.getTags();
                    blog.setTags(tag.replace(",", " "));
                    redisCache.put(blog.getBlogCode(), blog);
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
    public Map<String, Object> updateBlog(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        String blog_title= MapUtils.getString(form, "blog_title");
        String blog_code = Utils.md5(blog_title);
        form.put("blog_code", blog_code);
        String newTag = MapUtils.getString(form, "bolg_tags");

        if (blog_code != null){
            try {
                commonDao.update("blogTag.updateBlog", form);               //更新blog
                String oldTag = commonDao.get("blogTag.selecTagsByBlog",form);
                Set<String> newTags = compareTags(getTags(newTag), getTags(oldTag));
                for (String tag : newTags){
                    Map<String, Object> param = new HashMap<>();
                    param.put("blog_code", blog_code);
                    String tag_code = Utils.md5(tag);
                    param.put("tag_code", tag_code);
                    param.put("tag_name", tag);
                    Tag t = commonDao.get("blogTag.selectTag", param);
                    if (t == null){
                        commonDao.save("blogTag.addTag", param);
                    }
                    commonDao.save("blogTag.insertTagBlog", param);
                }
                result.put("code", 201);
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

    @Override
    @Transactional
    public Map<String, Object> deleteBlog(Map<String, Object> form) {
        Map<String,Object> result = new HashMap<>();
        String blog_code = MapUtils.getString(form, "blog_code");
        Blog blog = commonDao.get("blogTag.selectBlogForShow",form);
        if (blog_code != null && blog != null){
            try {
                commonDao.delete("blogTag.deleteBlog", form);
                commonDao.delete("blogTag.deleteTagBlog", form);
                result.put("code", 201);
                result.put("msg", "删除指定博客成功");
                logger.info("删除博客,blog_code = " + blog_code + "," +
                        "\n" + Utils.objectToJson(blog));

                String indexName = ESUtils.getEsIndexNameFromClazz(Blog.class);
                String type = ESUtils.getEsTypeFromClazz(Blog.class);

                SearchResponse response = esClient.preciseSearch(indexName, type, "blogCode", blog_code);
                SearchHits hits = response.getHits();
                if (hits.getTotalHits() == 1){
                    SearchHit hit = hits.getHits()[0];
                    String id = hit.getId();
                    esClient.deleteDocument(indexName, type, id);
                    logger.info("从es中删除数据: indexName = " + indexName + ",type = " + type  + ",Id = " + id + "\n " + hit.toString());
                }
                return result;
            }catch (Exception e){
                e.printStackTrace();
                logger.info(e);
                result.put("code", 501);
                result.put("msg", "服务器内部错误，请查看日志");
            }
        }else {
            result.put("code", 401);
            result.put("msg", "删除博客失败，不存在该博客");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectAdvBlogs(Map<String, Object> from) {
        Map<String,Object> result = new HashMap<>();
        String key = "Java";
        Map<String,Object> param = new HashMap<>();
        param.put("blogTilte",key );
        param.put("tags", key);
        List<List<Blog>> advs = new ArrayList<>();
        List<Blog> list1 = esService.fuzzySearch(param, 1, 4);
        advs.add(list1);
        key = "linux";
        param.put("blogTilte",key );
        param.put("tags", key);
        List<Blog> list2 = esService.fuzzySearch(param, 1, 4);
        advs.add(list2);
        result.put("data", advs);
        return result;
    }

    @Override
    public Map<String, Object> selectTags() {
        Map<String,Object> result = new HashMap<>();
        try {
            List<Tag> list = blogDao.selectTags();
            result.put("data", list);
            result.put("code", 201);
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e);
        }
        return result;
    }

    @Override
    @Permission(value = {PermissionEnum.SELECT})
    public Map<String, Object> test(Map<String, Object> form) {
        System.out.println("test");
        return form;
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
