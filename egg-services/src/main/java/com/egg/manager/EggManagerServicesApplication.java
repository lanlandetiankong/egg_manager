package com.egg.manager;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author zhoucj
 * @description Springboot启动类
 * @date 2020/06/07
 */
@EnableCaching
@EnableDubbo(scanBasePackages = {"com.egg.manager.api.services"})
@EnableMongoAuditing
@EnableMongoRepositories
@EnableEncryptableProperties
@SpringBootApplication
public class EggManagerServicesApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = SpringApplication.run(EggManagerServicesApplication.class, args);
    }
}
