package com.egg.manager.persistence.pojo.mongo.mapstruct.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldTypeDefinitionMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.baseExtend.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormFieldTypeDefinitionMVO;
import com.egg.manager.persistence.pojo.mongo.transfer.forms.SmartFormFieldTypeDefinitionTransfer;
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
        uses = {SmartFormFieldTypeDefinitionTransfer.class})
public interface SmartFormFieldTypeDefinitionMapstruct  extends MyBaseMongoMapstruct<SmartFormFieldTypeDefinitionMO,SmartFormFieldTypeDefinitionMVO> {
    SmartFormFieldTypeDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormFieldTypeDefinitionMapstruct.class);


    @Mappings({})
    SmartFormFieldTypeDefinitionMO translateMvoToMo(SmartFormFieldTypeDefinitionMVO mvo);
}
