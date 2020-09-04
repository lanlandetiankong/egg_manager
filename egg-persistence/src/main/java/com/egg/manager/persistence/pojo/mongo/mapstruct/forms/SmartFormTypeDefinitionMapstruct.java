package com.egg.manager.persistence.pojo.mongo.mapstruct.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormTypeDefinitionMVO;
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
public interface SmartFormTypeDefinitionMapstruct {
    SmartFormTypeDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormTypeDefinitionMapstruct.class);

    SmartFormTypeDefinitionMO mvo_CopyTo_MO(SmartFormTypeDefinitionMVO mvo);
}
