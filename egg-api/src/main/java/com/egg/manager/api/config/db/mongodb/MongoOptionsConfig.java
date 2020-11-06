package com.egg.manager.api.config.db.mongodb;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhoucj
 * @description mongodb 相关配置
 * @date 2020/10/21
 */
@Configuration
public class MongoOptionsConfig {


    @Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions
                .builder()
                .maxConnectionIdleTime(60000)
                .build();
    }

}
