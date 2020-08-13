package com.egg.manager;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableCaching
@EnableMongoAuditing
@EnableEncryptableProperties
@MapperScan(basePackages = "com.egg.manager.persistence.mapper")
public class EggManagerMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(EggManagerMessageApplication.class, args);
    }
}
