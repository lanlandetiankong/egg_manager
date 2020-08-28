package com.egg.manager;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@ImportResource(locations = {
        "classpath:dubbo/${egg.application.build.env}/egg-baseService-dubbo-config.xml",
        "classpath*:dubbo/${egg.application.build.env}/provider/egg-baseService-dubbo-*.xml",
        "classpath:dubbo/${egg.application.build.env}/consumer/egg-universal-dubbo-*.xml"
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
