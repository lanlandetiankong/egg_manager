package com.egg.manager.persistence.em.forms.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.em.forms.pojo.mapstruct.conversion.SmartFormTypeDefinitionConversion;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.em.forms.pojo.mvo.SmartFormTypeDefinitionMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description: 表单类型
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormTypeDefinitionConversion.class})
public interface SmartFormTypeDefinitionMapstruct extends MyBaseMongoMapstruct<SmartFormTypeDefinitionMgo, SmartFormTypeDefinitionMgvo> {
    SmartFormTypeDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormTypeDefinitionMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    SmartFormTypeDefinitionMgo translateMgvoToMgo(SmartFormTypeDefinitionMgvo mgvo);
}
