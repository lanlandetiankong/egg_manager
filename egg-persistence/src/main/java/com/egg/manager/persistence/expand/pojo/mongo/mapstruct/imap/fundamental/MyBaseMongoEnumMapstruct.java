package com.egg.manager.persistence.expand.pojo.mongo.mapstruct.imap.fundamental;

import com.egg.manager.persistence.expand.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.expand.pojo.mongo.mvo.BaseModelMgvo;
import org.mapstruct.MapperConfig;

/**
 * @author zhoucj
 * @description: 可在该接口写公用的转化方法, 定义的方法请勿修改方法名、参数、返回值等！枚举相关方法 default定义
 * @date 2020/10/20
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMongoEnumMapstruct<E extends MyBaseModelMgo, V extends BaseModelMgvo> {


}
