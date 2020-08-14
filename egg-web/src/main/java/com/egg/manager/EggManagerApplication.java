package com.egg.manager;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableCaching
@EnableMongoAuditing
@EnableEncryptableProperties
public class EggManagerApplication {
	public static void main(String[] args) {
        SpringApplication.run(EggManagerApplication.class, args);
    }
}
