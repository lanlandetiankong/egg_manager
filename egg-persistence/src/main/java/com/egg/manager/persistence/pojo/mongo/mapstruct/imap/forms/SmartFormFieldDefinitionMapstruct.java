package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMgo;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.forms.SmartFormFieldDefinitionConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormFieldDefinitionMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:表单类型
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
