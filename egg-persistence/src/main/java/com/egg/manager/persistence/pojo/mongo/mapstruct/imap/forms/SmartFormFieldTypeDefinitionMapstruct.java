package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldTypeDefinitionMgo;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.forms.SmartFormFieldTypeDefinitionConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormFieldTypeDefinitionMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * \* note: 表单 字段类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:23
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormFieldTypeDefinitionConversion.class})
public interface SmartFormFieldTypeDefinitionMapstruct extends MyBaseMongoMapstruct<SmartFormFieldTypeDefinitionMgo, SmartFormFieldTypeDefinitionMgvo> {
    SmartFormFieldTypeDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormFieldTypeDefinitionMapstruct.class);


    @Mappings({})
    SmartFormFieldTypeDefinitionMgo translateMvoToMo(SmartFormFieldTypeDefinitionMgvo mvo);
}
