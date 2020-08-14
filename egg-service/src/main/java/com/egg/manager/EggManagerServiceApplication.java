package com.egg.manager;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableCaching
@EnableDubbo
@EnableMongoAuditing
@EnableEncryptableProperties
public class EggManagerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EggManagerServiceApplication.class, args);
    }
}
