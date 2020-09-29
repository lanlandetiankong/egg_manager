package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import org.mapstruct.MapperConfig;

/**
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 * <p>
 * <p>
 * Notes:
 *
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMongoMapstruct<Mgo extends MyBaseModelMgo, MVO extends BaseModelMgvo>
        extends MyBaseMongoCommonFuncMapstruct<Mgo, MVO>, MyBaseMongoConstantMapstruct<Mgo, MVO>, MyBaseMongoEnumMapstruct<Mgo, MVO> {


}
