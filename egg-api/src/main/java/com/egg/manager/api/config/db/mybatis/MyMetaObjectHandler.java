package com.egg.manager.api.config.db.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.egg.manager.persistence.constant.pojo.mysql.MyBaseMysqlEntityFieldConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: mybatisplus 字段填充
 * @ClassName: MyMetaObjectHandler
 * @Author: zhoucj
 * @Date: 2020/9/30 13:50
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date() ;
        this.strictInsertFill(metaObject, MyBaseMysqlEntityFieldConstant.CREATE_TIME.getFieldName(), MyBaseMysqlEntityFieldConstant.CREATE_TIME.getClazz(), now);
        this.strictInsertFill(metaObject, MyBaseMysqlEntityFieldConstant.UPDATE_TIME.getFieldName(), MyBaseMysqlEntityFieldConstant.UPDATE_TIME.getClazz(), now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date now = new Date() ;
        this.strictInsertFill(metaObject, MyBaseMysqlEntityFieldConstant.UPDATE_TIME.getFieldName(), MyBaseMysqlEntityFieldConstant.UPDATE_TIME.getClazz(), now);
    }
}
