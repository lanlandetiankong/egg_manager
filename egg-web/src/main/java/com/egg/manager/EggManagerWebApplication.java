package com.egg.manager;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.egg.manager.common.annotation.teaegg.EnableEggBeanScan;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author zhoucj
 * @description: Springboot启动类
 * @date 2020/06/07
 */
@SpringBootApplication
@EnableJms
@EnableCaching
@EnableDubbo
@EnableMongoAuditing
@EnableMongoRepositories
@EnableEncryptableProperties
@EnableEggBeanScan(basePackages = {"com.egg.manager"})
public class EggManagerWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(EggManagerWebApplication.class, args);
    }
}
