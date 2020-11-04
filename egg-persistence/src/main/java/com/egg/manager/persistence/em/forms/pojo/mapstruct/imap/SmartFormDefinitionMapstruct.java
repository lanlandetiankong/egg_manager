package com.egg.manager.persistence.em.forms.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormDefinitionMgo;
import com.egg.manager.persistence.em.forms.pojo.mapstruct.conversion.SmartFormDefinitionConversion;
import com.egg.manager.persistence.expand.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.em.forms.pojo.mvo.SmartFormDefinitionMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormDefinitionConversion.class}
)
public interface SmartFormDefinitionMapstruct extends MyBaseMongoMapstruct<SmartFormDefinitionMgo, SmartFormDefinitionMgvo> {
    SmartFormDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormDefinitionMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({
            @Mapping(target = "formType", expression = "java(commonTranslateSmartFormTypeDefinitionMgvoToMo(mgvo.getFormType()))")
    })
    SmartFormDefinitionMgo translateMgvoToMgo(SmartFormDefinitionMgvo mgvo);
}
