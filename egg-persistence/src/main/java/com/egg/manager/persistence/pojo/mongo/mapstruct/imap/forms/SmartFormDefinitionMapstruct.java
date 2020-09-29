package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormDefinitionMgo;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.forms.SmartFormDefinitionConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormDefinitionMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 22:35
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormDefinitionConversion.class}
)
public interface SmartFormDefinitionMapstruct extends MyBaseMongoMapstruct<SmartFormDefinitionMgo, SmartFormDefinitionMgvo> {
    SmartFormDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormDefinitionMapstruct.class);

    @Mappings({
            @Mapping(target = "formType", expression = "java(commonTranslateSmartFormTypeDefinitionMVOToMO(mvo.getFormType()))")
    })
    SmartFormDefinitionMgo translateMvoToMo(SmartFormDefinitionMgvo mvo);
}
