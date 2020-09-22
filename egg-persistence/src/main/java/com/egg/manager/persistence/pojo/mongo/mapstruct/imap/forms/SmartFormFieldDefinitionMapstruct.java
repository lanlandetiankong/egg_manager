package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.forms.SmartFormFieldDefinitionConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.baseExtend.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormFieldDefinitionMVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * \* note: 表单类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:06
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormFieldDefinitionConversion.class}
)
public interface SmartFormFieldDefinitionMapstruct extends MyBaseMongoMapstruct<SmartFormFieldDefinitionMO, SmartFormFieldDefinitionMVO> {
    SmartFormFieldDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormFieldDefinitionMapstruct.class);


    @Mappings({})
    SmartFormFieldDefinitionMO translateMvoToMo(SmartFormFieldDefinitionMVO mvo);
}