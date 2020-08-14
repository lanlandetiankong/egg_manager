package com.egg.manager.persistence.mongo.mapstruct.forms;

import com.egg.manager.persistence.mongo.mo.forms.SmartFormDefinitionMO;
import com.egg.manager.persistence.mongo.mvo.forms.SmartFormDefinitionMVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 22:35
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring")
public interface SmartFormDefinitionMapstruct {
    SmartFormDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormDefinitionMapstruct.class);

    SmartFormDefinitionMO mvo_CopyTo_MO(SmartFormDefinitionMVO mvo);
}
