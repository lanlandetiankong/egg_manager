package com.egg.manager.common.override.org.springframework.data.mongodb.core.query;


import com.egg.manager.common.override.org.springframework.data.mongodb.core.IBaseOverrideMongoQuery;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 继承的 spring-Mongo 的 Query，并实现 序列化
 */
public class InheritMongoQuery extends Query implements IBaseOverrideMongoQuery<InheritMongoQuery,Query> {

    @Override
    public Query getSuperBean() {
        return (Query) this;
    }

    public static InheritMongoQuery getSelfBean(Query query) {
        return (query == null) ? null : (InheritMongoQuery)query;
    }
}
