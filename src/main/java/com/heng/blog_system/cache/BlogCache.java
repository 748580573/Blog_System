package com.heng.blog_system.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.heng.blog_system.bean.Blog;
import com.heng.blog_system.bean.Tag;
import com.heng.blog_system.db.BlogDao;
import com.heng.blog_system.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class BlogCache {

    private Logger logger = Logger.getLogger(BlogCache.class);

    @Autowired
    private BlogDao blogDao;

    private LoadingCache<String, Tag> tags = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<String, Tag>() {
                        @Override
                        public Tag load(String key) throws Exception {
                            Map<String,Object> map = new HashMap<>();
                            map.put("tag_code", Utils.md5(key));
                            return blogDao.get("blogTag.selectTag",map);
                        }
                    }
            );


    public Tag getTag(String key){
        try {
            Tag tag = tags.get(key);
            return tag;
        } catch (Exception e) {
            logger.info("缓存种查找" + key + "所代表的tag失败");
            return null;
        }
    }

    public void putTag(String key,Tag tag){
        tags.put(key,tag );
    }

    /**
     * 用于首页缓存
     */
    private LoadingCache<String, Blog> blogs = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<String, Blog>() {
                        @Override
                        public Blog load(String key) throws Exception {
                            Map<String,Object> map = new HashMap<>();
                            map.put("blog_code", key);
                            Blog blog = blogDao.get("blogTag.selectBlogFormIndex",map);
                            return blog;
                        }
                    }
            );

    public Blog getBlog(String key) {
        Blog blog = null;
        try {
            blog = blogs.get(key);
        } catch (Exception e) {
            logger.info("缓存中不存在该blog: " +key);
            blog = null;
        }
        return blog;
    }

    public void putBlog(String key,Blog blog){
        blogs.put(key, blog);
    }

}
