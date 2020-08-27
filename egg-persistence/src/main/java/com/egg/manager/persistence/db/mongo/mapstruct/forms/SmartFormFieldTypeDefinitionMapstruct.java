package com.egg.manager.persistence.db.mongo.mapstruct.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormFieldTypeDefinitionMO;
import com.egg.manager.persistence.db.mongo.mvo.forms.SmartFormFieldTypeDefinitionMVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * \* note: 表单 字段类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:23
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring")
public interface SmartFormFieldTypeDefinitionMapstruct {
    SmartFormFieldTypeDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormFieldTypeDefinitionMapstruct.class);


    SmartFormFieldTypeDefinitionMO mvo_CopyTo_MO(SmartFormFieldTypeDefinitionMVO mvo);
}
