package com.example.demo.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TokenBlacklistService {

    @Autowired
    private CacheManager cacheManager;

    //use @Cacheable so that Spring manage the cache for us
    @Cacheable(value = "default", key = "#userId + '_blackListJti'")
    public List<String> getValueFromCache(String userId) {
        return null;
    }

    @CachePut(value = "default", key = "#userId + '_blackListJti'")
    public List<String> updateCacheValue(String userId, List<String> newValue) {
        return newValue;
    }

    @CacheEvict(value = "default", key = "#userId + '_blackListJti'")
    public void evictCache(String userId) {
    }

//Manually interacting with CacheManager
//    public List<String> getBlackListTokens(String userId) {
//        Cache cache = cacheManager.getCache("default");
//        if (cache != null) {
//            Cache.ValueWrapper valueWrapper = cache.get(userId + "_blackListJti");
//            if (valueWrapper != null) {
//                return (List<String>) valueWrapper.get();
//            }
//        }
//        return new ArrayList<>();
//    }
//
//    public void storeBlackListTokens(String userId, List<String> tokens) {
//        Cache cache = cacheManager.getCache("default");
//        String cacheKey = userId + "_blackListJti";
//        if (cache != null) {
//            cache.put(cacheKey, tokens);
//        }
//    }
}

