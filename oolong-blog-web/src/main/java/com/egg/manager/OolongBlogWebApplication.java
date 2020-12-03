package com.egg.manager;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.annotation.EnableJms;

/**
 * @description: Springboot启动类
 * @author zhoucj
 * @date 2020/11/30
 */
@SpringBootApplication
@EnableJms
@EnableCaching
@EnableDubbo
@EnableMongoAuditing
@EnableMongoRepositories
@EnableEncryptableProperties
public class OolongBlogWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(OolongBlogWebApplication.class, args);
    }
}