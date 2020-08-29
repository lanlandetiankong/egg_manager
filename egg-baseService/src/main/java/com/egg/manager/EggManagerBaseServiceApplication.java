package com.egg.manager;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@ImportResource(locations = {
        "classpath:${egg.application.build.env}/dubbo/egg-baseService-dubboConfig.xml",
        "classpath*:${egg.application.build.env}/dubbo/provider/egg-baseService-dubboProvider-*.xml",
        "classpath:universal/${egg.application.build.env}/dubbo/consumer/*/egg-universal-dubboConsumer-*.xml"
})
@EnableCaching
@EnableDubbo
@EnableMongoAuditing
@EnableEncryptableProperties
@SpringBootApplication
public class EggManagerBaseServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EggManagerBaseServiceApplication.class, args);
    }
}
