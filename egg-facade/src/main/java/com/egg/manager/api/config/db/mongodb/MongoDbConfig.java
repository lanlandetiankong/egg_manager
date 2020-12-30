package com.egg.manager.api.config.db.mongodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * @author zhoucj
 * @description mongodb 相关配置
 * @date 2020/10/20
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class MongoDbConfig {

    private final MongoTemplate mongoTemplate;
    private final MongoConverter mongoConverter;

    /**
     * @since spring data mongo 3.0 需要引用该段配置，否则启动时会抛异常
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext = this.mongoConverter.getMappingContext();
        if (mappingContext instanceof MongoMappingContext) {
            MongoMappingContext mongoMappingContext = (MongoMappingContext) mappingContext;
            //自动创建索引
            mongoMappingContext.setAutoIndexCreation(true);
            for (BasicMongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
                Class clazz = persistentEntity.getType();
                if (clazz.isAnnotationPresent(Document.class)) {
                    MongoPersistentEntityIndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
                    IndexOperations indexOps = this.mongoTemplate.indexOps(clazz);
                    resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
                }
            }
        }
    }
}
