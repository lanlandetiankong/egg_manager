package com.egg.manager.persistence.em.forms.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormFieldTypeDefinitionMgo;
import com.egg.manager.persistence.em.forms.pojo.mapstruct.conversion.SmartFormFieldTypeDefinitionConversion;
import com.egg.manager.persistence.expand.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.em.forms.pojo.mvo.SmartFormFieldTypeDefinitionMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:表单 字段类型
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormFieldTypeDefinitionConversion.class})
public interface SmartFormFieldTypeDefinitionMapstruct extends MyBaseMongoMapstruct<SmartFormFieldTypeDefinitionMgo, SmartFormFieldTypeDefinitionMgvo> {
    SmartFormFieldTypeDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormFieldTypeDefinitionMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    SmartFormFieldTypeDefinitionMgo translateMgvoToMgo(SmartFormFieldTypeDefinitionMgvo mgvo);
}
