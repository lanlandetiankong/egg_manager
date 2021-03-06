package com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental;

import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.exchange.pojo.mongo.mvo.BaseModelMgvo;
import org.mapstruct.MapperConfig;

/**
 * @author zhoucj
 * @description可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 * @date 2020/10/20
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMongoCommonFuncMapstruct<E extends MyBaseModelMgo, V extends BaseModelMgvo> {



}
