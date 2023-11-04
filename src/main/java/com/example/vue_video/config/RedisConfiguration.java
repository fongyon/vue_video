package com.example.vue_video.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/*
* redis配置类 ---- 序列化
* */
@Configuration
@Slf4j
public class RedisConfiguration {
    /**
     * 使用自带的redisTemplate不能把（对象等）序列化
     * RedisTemplate：默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的
     * StringRedisTemplate：默认采用的是String的序列化策略，保存的key和value都是采用此策略序列化保存的(StringRedisSerializer)。
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis模板对象");
        RedisTemplate redisTemplate = new RedisTemplate();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis的key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }
}

