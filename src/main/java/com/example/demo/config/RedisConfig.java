package com.example.demo.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import sun.rmi.runtime.Log;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.max-wait}")
    private int maxWait;

    @Bean
    public JedisPool redisPoolFactory() {
        Logger.getLogger(getClass()).info("======================: JedisPool注入成功");
        Logger.getLogger(getClass()).info("======================: Redis地址：" + host + ":" + port);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        JedisPool pool = new JedisPool(config, host, port, timeout);
        return pool;
    }
}
