package com.egg.manager;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@EnableCaching
@EnableDubbo
@EnableMongoAuditing
@EnableEncryptableProperties
@SpringBootApplication
public class EggManagerBaseServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = SpringApplication.run(EggManagerBaseServiceApplication.class, args);
    }
}