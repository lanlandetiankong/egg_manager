package com.egg.manager.baseService.services.redis.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.egg.manager.api.services.redis.service.RedisHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(interfaceClass = RedisHelper.class)
public class RedisHelperImpl<HK, T> implements RedisHelper<HK, T> {
    /**
     * 在构造器中获取RedisTemplate实例，key(not hashKey) 默认使用String类型
     */
    private RedisTemplate<String, T> redisTemplate;
    /**
     * 在构造器中通过RedisTemplate的工厂方法
     */
    private HashOperations<String, HK, T> hashOperations;
    private ListOperations<String, T> listOperations;
    private ZSetOperations<String, T> zSetOperations;
    private SetOperations<String, T> setOperations;
    private ValueOperations<String, T> valueOperations;

    @Autowired
    public RedisHelperImpl(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.zSetOperations = redisTemplate.opsForZSet();
        this.setOperations = redisTemplate.opsForSet();
        this.valueOperations = redisTemplate.opsForValue();
    }

    /**
     * Hash结构 添加元素 * @param key key * @param hashKey hashKey * @param domain 元素
     *
     * @param key
     * @param hashKey
     * @param domain
     */
    @Override
    public void hashPut(String key, HK hashKey, T domain) {
        if (domain instanceof String) {
            hashOperations.put(key, hashKey, domain);
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                T domainJson = (T) mapper.writeValueAsString(domain);
                hashOperations.put(key, hashKey, domainJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Hash结构 添加元素 * @param key key * @param hashKey hashKey * @param domain 元素
     *
     * @param key
     * @param hashKey
     * @param domain
     */
    @Override
    public boolean hashTtlPut(String key, HK hashKey, T domain, Long timeout) {
        hashPut(key, hashKey, domain);
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * Hash结构 获取指定key所有键值对 * @param key * @return
     *
     * @param key
     */
    @Override
    public Map<HK, T> hashFindAll(String key) {
        return hashOperations.entries(key);
    }

    /**
     * Hash结构 获取单个元素 * @param key * @param hashKey * @return
     *
     * @param key
     * @param hashKey
     */
    @Override
    public T hashGet(String key, Object hashKey) {
        return hashOperations.get(key, hashKey);
    }

    @Override
    public void hashRemove(String key, Object hashKey) {
        hashOperations.delete(key, hashKey);
    }

    /**
     * List结构 向尾部(Right)添加元素 * @param key * @param domain * @return
     *
     * @param key
     * @param domain
     */
    @Override
    public Long listPush(String key, T domain) {
        return listOperations.rightPush(key, domain);
    }

    /**
     * List结构 向头部(Left)添加元素 * @param key * @param domain * @return
     *
     * @param key
     * @param domain
     */
    @Override
    public Long listUnshift(String key, T domain) {
        return listOperations.leftPush(key, domain);
    }

    /**
     * List结构 获取所有元素 * @param key * @return
     *
     * @param key
     */
    @Override
    public List<T> listFindAll(String key) {
        if (!redisTemplate.hasKey(key)) {
            return null;
        }
        return listOperations.range(key, 0, listOperations.size(key));
    }

    /**
     * List结构 移除并获取数组第一个元素 * @param key * @return
     *
     * @param key
     */
    @Override
    public T listLPop(String key) {
        return listOperations.leftPop(key);
    }

    /**
     * 对象的实体类
     *
     * @param key
     * @param domain
     * @return
     */
    @Override
    public void valuePut(String key, T domain) {
        valueOperations.set(key, domain);
    }

    /**
     * 有时限的 对象的实体类
     *
     * @param key
     * @param domain
     * @param timeout
     * @param timeUnit
     * @return
     */
    @Override
    public boolean valuePut(String key, T domain, long timeout, TimeUnit timeUnit) {
        this.valuePut(key, domain);
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 获取对象实体类
     *
     * @param key
     * @return
     */
    @Override
    public T getValue(String key) {
        return valueOperations.get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置过期时间 * @param key 键 * @param timeout 时间 * @param timeUnit 时间单位
     *
     * @param key
     * @param timeout
     * @param timeUnit
     */
    @Override
    public boolean expirse(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }


    @Override
    public boolean isExistKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
