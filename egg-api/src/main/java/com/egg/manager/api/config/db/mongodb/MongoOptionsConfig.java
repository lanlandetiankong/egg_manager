package com.egg.manager.api.config.db.mongodb;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * \* note: mongodb 相关配置
 * \* User: zhouchengjie
 * \* Date: 2020/8/11
 * \* Time: 9:36
 * \* Description:
 * \
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
