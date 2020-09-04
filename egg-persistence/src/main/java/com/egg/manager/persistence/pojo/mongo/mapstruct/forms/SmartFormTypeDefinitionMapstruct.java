package com.egg.manager.persistence.pojo.mongo.mapstruct.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormTypeDefinitionMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.baseExtend.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormTypeDefinitionMVO;
import com.egg.manager.persistence.pojo.mongo.transfer.forms.SmartFormTypeDefinitionTransfer;
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
        uses = {SmartFormTypeDefinitionTransfer.class})
public interface SmartFormTypeDefinitionMapstruct  extends MyBaseMongoMapstruct<SmartFormTypeDefinitionMO,SmartFormTypeDefinitionMVO> {
    SmartFormTypeDefinitionMapstruct INSTANCE = Mappers.getMapper(SmartFormTypeDefinitionMapstruct.class);

    @Mappings({})
    SmartFormTypeDefinitionMO translateMvoToMo(SmartFormTypeDefinitionMVO mvo);
}
