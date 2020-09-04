package com.egg.manager.persistence.pojo.mongo.mapstruct.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldDefinitionMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.baseExtend.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormFieldDefinitionMVO;
import com.egg.manager.persistence.pojo.mongo.transfer.forms.SmartFormFieldDefinitionTransfer;
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
        uses = {SmartFormFieldDefinitionTransfer.class})
public interface SmartFormFieldDefinitionMapstruct extends MyBaseMongoMapstruct<SmartFormFieldDefinitionMO,SmartFormFieldDefinitionMVO> {
    SmartFormFieldDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormFieldDefinitionMapstruct.class);


    @Mappings({})
    SmartFormFieldDefinitionMO translateMvoToMo(SmartFormFieldDefinitionMVO mvo);
}
