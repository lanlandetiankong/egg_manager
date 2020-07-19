package com.egg.manager.persistence.mongo.daoimpl.log;

import com.egg.manager.persistence.mongo.dao.log.OperationLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.config.MongoConfigurationSupport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

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
