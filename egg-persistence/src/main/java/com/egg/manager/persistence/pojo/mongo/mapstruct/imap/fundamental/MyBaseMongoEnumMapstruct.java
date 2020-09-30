package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.organization.DefineTenantMapstruct;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user.UserAccountMapstruct;
import org.mapstruct.MapperConfig;

/**
 * 可在该接口写公用的转化方法,定义的方法请勿修改方法名、参数、返回值等！
 * <p>
 * 枚举相关方法 default定义
 * Notes:
 *
 * @Context : 该注解用于添加额外参数，不会对其进行判断null等操作
 */
@MapperConfig(disableSubMappingMethodsGeneration = true)
public interface MyBaseMongoEnumMapstruct<E extends MyBaseModelMgo, V extends BaseModelMgvo> {


}
