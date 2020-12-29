package com.egg.manager.facade.persistence.em.forms.pojo.mapstruct.imap;

import com.egg.manager.facade.persistence.em.forms.db.mongo.mo.SmartFormFieldDefinitionMgo;
import com.egg.manager.facade.persistence.em.forms.pojo.mapstruct.conversion.SmartFormFieldDefinitionConversion;
import com.egg.manager.facade.persistence.em.forms.pojo.mvo.SmartFormFieldDefinitionMgvo;
import com.egg.manager.facade.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description表单类型
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormFieldDefinitionConversion.class}
)
public interface SmartFormFieldDefinitionMapstruct extends MyBaseMongoMapstruct<SmartFormFieldDefinitionMgo, SmartFormFieldDefinitionMgvo> {
    SmartFormFieldDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormFieldDefinitionMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    SmartFormFieldDefinitionMgo translateMgvoToMgo(SmartFormFieldDefinitionMgvo mgvo);
}
