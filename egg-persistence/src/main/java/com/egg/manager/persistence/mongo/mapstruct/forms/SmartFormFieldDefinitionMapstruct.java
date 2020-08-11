package com.egg.manager.persistence.mongo.mapstruct.forms;

import com.egg.manager.persistence.mongo.mo.forms.SmartFormFieldDefinitionMO;
import com.egg.manager.persistence.mongo.mvo.forms.SmartFormFieldDefinitionMVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * \* note: 表单类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:06
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring")
public interface SmartFormFieldDefinitionMapstruct {
    SmartFormFieldDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormFieldDefinitionMapstruct.class);


    SmartFormFieldDefinitionMO mvo_CopyTo_MO(SmartFormFieldDefinitionMVO mvo);
}
