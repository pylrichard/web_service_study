package com.bd.roncoo.spring.boot.cache.impl;

import com.bd.roncoo.spring.boot.bean.UserLog;
import com.bd.roncoo.spring.boot.cache.UserLogCache;
import com.bd.roncoo.spring.boot.dao.UserLogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * 报错redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool时解决办法有
 * 1 重启redis-server
 * 2 不指定配置文件启动 redis-server &
 *
 * EhCache与Redis不同，它作为jar嵌入Java应用，与Java应用绑定在一起
 *
 * 见Spring缓存注解@Cacheable、@CacheEvict、@CachePut使用
 */
@CacheConfig(cacheNames = "user_log_cache")
@Repository
public class UserLogCacheImpl implements UserLogCache {
    @Autowired
    private UserLogDAO userLogDao;

    /**
     * 自动根据方法生成缓存
     *
     * key属性用来指定Spring对方法的返回结果进行缓存时对应的key
     * 自定义策略可以通过Spring的EL表达式来指定key
     * EL表达式可以使用方法参数及它们对应的属性
     * 使用方法参数时可以使用"#参数名"或者"#p参数index"
     */
    @Cacheable(key = "#id")
    @Override
    public UserLog selectById(Integer id) {
        System.out.println("selectById id = " + id);
        return userLogDao.findOne(id);
    }

    @CachePut(key = "#p0.userIp")
    @Override
    public UserLog updateById(UserLog userLog) {
        System.out.println("updateById id = " + userLog);
        return userLogDao.save(userLog);
    }

    @CacheEvict(key = "#p0")
    @Override
    public void deleteById(Integer id) {
        System.out.println("deleteById id = " + id);
    }
}
