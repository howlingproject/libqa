package com.libqa.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {
    public static final String CACHE_DISPLAY_INDEX = "cache.displayIndex";
    public static final String CACHE_USER_EMAIL = "cache.userEmail";

    @Bean
    @Override
    public CacheManager cacheManager() {
        log.info("cacheManager is loaded.");

        SimpleCacheManager cacheManager = new SimpleCacheManager();

        GuavaCache displayIndexCache = new GuavaCache(CACHE_DISPLAY_INDEX, CacheBuilder.newBuilder()
                .expireAfterWrite(360, TimeUnit.MINUTES)
                .build());

        GuavaCache userEmailCache = new GuavaCache(CACHE_USER_EMAIL, CacheBuilder.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build());

        cacheManager.setCaches(Lists.newArrayList(displayIndexCache, userEmailCache));
        return cacheManager;
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}
