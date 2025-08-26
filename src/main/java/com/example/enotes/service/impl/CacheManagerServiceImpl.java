package com.example.enotes.service.impl;

import com.example.enotes.service.CacheManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class CacheManagerServiceImpl implements CacheManagerService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Collection<String> getCache() {
        Collection<String> cacheNames = cacheManager.getCacheNames();

        for (String cacheName:cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);

            log.info("Cache name = {}", cache);
        }
        return cacheNames;
    }

    @Override
    public Cache getCacheName(String cacheName) {

        Cache cache = cacheManager.getCache(cacheName);
        log.info("Cache name = {}", cache);

        return cache;
    }

    @Override
    public void removeAllCache() {
        Collection<String> cacheNames = cacheManager.getCacheNames();

        for (String cacheName:cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            log.info("Cache name = {}", cache);
            cache.clear();
        }
    }

    @Override
    public void removeCacheByName(List<String> cacheNames) {
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            log.info("Cache name = {}", cache);

            cache.clear();
        }
    }
}
