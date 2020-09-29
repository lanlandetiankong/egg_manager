package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMgo;
import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.organization.DefineTenantMapstruct;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user.UserAccountMapstruct;
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
public interface MyBaseMongoConstantMapstruct<E extends MyBaseModelMgo, V extends BaseModelMgvo> {

    UserAccountMapstruct userAccountMapstruct = UserAccountMapstruct.INSTANCE;
    DefineTenantMapstruct defineTenantMapstruct = DefineTenantMapstruct.INSTANCE;


}
