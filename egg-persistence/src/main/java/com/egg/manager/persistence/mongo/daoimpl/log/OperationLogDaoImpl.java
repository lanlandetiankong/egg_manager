package com.egg.manager.persistence.mongo.daoimpl.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * <p>
 * 操作日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie
 * @since 2020-07-19
 */
public class OperationLogDaoImpl<T>  {
    @Autowired
    private MongoTemplate mongoTemplate ;


}
