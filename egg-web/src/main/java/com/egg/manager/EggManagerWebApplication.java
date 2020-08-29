package com.egg.manager;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.jms.annotation.EnableJms;


@ImportResource(locations = {
        "classpath:${egg.application.build.env}/dubbo/egg-web-dubboConfig.xml",
        "classpath:universal/${egg.application.build.env}/dubbo/consumer/egg-universal-dubboConsumer-*.xml"
})
@SpringBootApplication
@EnableJms
@EnableCaching
@EnableDubbo
@EnableMongoAuditing
@EnableEncryptableProperties
public class EggManagerWebApplication {
	public static void main(String[] args) {
        SpringApplication.run(EggManagerWebApplication.class, args);
    }
}
