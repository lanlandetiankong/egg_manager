package com.egg.manager.persistence.enhance.pojo.mongo.mapstruct.imap.fundamental;

import com.egg.manager.persistence.enhance.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.enhance.pojo.mongo.mvo.BaseModelMgvo;
import org.mapstruct.MapperConfig;

/**
 * @author zhoucj
 * @description: 可在该接口写公用的转化方法, 定义的方法请勿修改方法名、参数、返回值等！
 * @date 2020/10/20
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMongoMapstruct<Mgo extends MyBaseModelMgo, MVO extends BaseModelMgvo>
        extends MyBaseMongoCommonFuncMapstruct<Mgo, MVO>, MyBaseMongoConstantMapstruct<Mgo, MVO>, MyBaseMongoEnumMapstruct<Mgo, MVO> {


}
