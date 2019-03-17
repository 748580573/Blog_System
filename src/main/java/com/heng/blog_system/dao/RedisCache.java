package com.heng.blog_system.dao;

import com.alibaba.druid.util.StringUtils;
import com.heng.blog_system.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisCache {

    @Autowired
    private RedisTemplate<String,String> cacheRedisTemplate;

    public void put(Object key,Object value){
        if (null == value){
            return;
        }

        if (value instanceof String){
            if (StringUtils.isEmpty(value.toString())){
                return;
            }
        }

        final String keyf = key + "";
        final Object valuef = value;
        final long liveTime = 60 * 60 * 24;

        cacheRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = Utils.toByteArray(valuef);
                redisConnection.set(keyb, valueb);
                if (liveTime > 0){
                    redisConnection.expire(keyb, liveTime);
                }
                return 1L;
            }
        });
    }

    public Object get(Object key){
        final String keyf = (String)key;
        Object object;
        object = cacheRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] key = keyf.getBytes();
                byte[] value = redisConnection.get(key);
                if (value == null){
                    return null;
                }
                return Utils.toObject(value);
            }
        });
        return object;
    }
}
