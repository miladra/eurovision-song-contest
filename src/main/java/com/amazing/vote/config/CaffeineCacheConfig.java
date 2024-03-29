package com.amazing.vote.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Milad Ranjbari
 * @version 2022.6.1
 * @since 6/01/22
 * This cache used for handle Idempotence for update price
 */

@Configuration
public class CaffeineCacheConfig {

    /**
     * This is the main configuration that will control caching behavior such as expiration, cache size limits, and more
     * this is a Caffeine cache with no max value and no expiration
     */
    @Bean
    public Cache<String, Long> caffeineConfig() {
        return Caffeine.newBuilder().build();
    }

}
