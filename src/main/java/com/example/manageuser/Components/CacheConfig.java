package com.example.manageuser.Components;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {


    @Bean
    public Cache<String,Object> caffineCache() {
        return Caffeine.newBuilder().build();
    }

    @Bean("caffineCacheHasTimeUnit")
    public Cache<String,Object> caffineCacheHasTimeUnit() {
        return Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();
    }


    @Bean
    public Cache<String,Long> accessStatisticCache() {
        return Caffeine.newBuilder().build();
    }



    @Bean
    public Cache<String, Set<String>> authorizationCache() {
        return Caffeine.newBuilder().build();
    }

}
