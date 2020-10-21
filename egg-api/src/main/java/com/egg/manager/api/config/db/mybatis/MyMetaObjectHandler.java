package com.egg.manager.api.config.db.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.egg.manager.persistence.constant.pojo.mysql.MyBaseMysqlEntityFieldConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhoucj
 * @description:mybatisplus 字段填充
 * @date 2020/10/21
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        this.strictInsertFill(metaObject, MyBaseMysqlEntityFieldConstant.CREATE_TIME.getFieldName(), MyBaseMysqlEntityFieldConstant.CREATE_TIME.getClazz(), now);
        this.strictInsertFill(metaObject, MyBaseMysqlEntityFieldConstant.UPDATE_TIME.getFieldName(), MyBaseMysqlEntityFieldConstant.UPDATE_TIME.getClazz(), now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date now = new Date();
        this.strictInsertFill(metaObject, MyBaseMysqlEntityFieldConstant.UPDATE_TIME.getFieldName(), MyBaseMysqlEntityFieldConstant.UPDATE_TIME.getClazz(), now);
    }
}
