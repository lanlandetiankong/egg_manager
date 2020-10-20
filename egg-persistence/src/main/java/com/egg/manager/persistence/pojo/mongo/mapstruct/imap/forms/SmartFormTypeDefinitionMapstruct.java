package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMgo;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.forms.SmartFormTypeDefinitionConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormTypeDefinitionMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * \* note: 表单类型
 * @author: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:06
 * \* Description:
 * \
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
