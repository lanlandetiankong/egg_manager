package com.egg.manager.common.override.org.springframework.data.mongodb.core.query;


import com.egg.manager.common.override.org.springframework.data.mongodb.core.IBaseOverrideMongoQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 继承的 spring-Mongo 的 Update，并实现 序列化
 */
public class InheritMongoUpdate extends Update implements IBaseOverrideMongoQuery<InheritMongoUpdate,Update> {
    @Override
    public Update getSuperBean() {
        return (Update) this;
    }

    public static InheritMongoUpdate getSelfBean(Update update) {
        return (update == null) ? null : (InheritMongoUpdate)update;
    }
}
