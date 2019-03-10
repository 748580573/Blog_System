package com.heng.blog_system.db.impl;

import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.db.CommonDao;
import com.heng.blog_system.db.ESService;
import com.heng.blog_system.db.RedisCache;
import com.heng.util.ESClient;
import com.heng.util.ESUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ESServiceImpl implements ESService {

    @Autowired
    private ESClient esClient;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CommonDao commonDao;

    private Logger logger = Logger.getLogger(ESServiceImpl.class);


    @Override
    public List<Blog> fuzzySearch(Map<String, Object> param, int pageNo, int pageSize) {
        List<Blog> list = new ArrayList<>();
        String indexName = ESUtils.getEsIndexNameFromClazz(Blog.class);
        String indexType = ESUtils.getEsTypeFromClazz(Blog.class);
        try {
            SearchResponse response =esClient.boolMulitSearchForShould(indexName, indexType, param,pageNo,pageSize);
            logger.info("站内搜索到的数据：" + response);
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits){
                String blogCode = hit.getSourceAsMap().get("blogCode").toString();
                Blog blog = (Blog) redisCache.get(blogCode);
                if (blog == null){
                    Map<String,Object> tempParam = new HashMap<>();
                    tempParam.put("blog_code", blogCode);
                    blog = commonDao.get("blogTag.selectBlogForIndex",tempParam);
                    redisCache.put(blogCode, blog);
                }
                list.add(blog);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
