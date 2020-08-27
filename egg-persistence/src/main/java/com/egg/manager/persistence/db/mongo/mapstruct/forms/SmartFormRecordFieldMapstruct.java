package com.egg.manager.persistence.db.mongo.mapstruct.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordFieldMO;
import com.egg.manager.persistence.db.mongo.mvo.forms.SmartFormRecordFieldMVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * \* note:表单项
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:58
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring")
public interface SmartFormRecordFieldMapstruct {
    SmartFormRecordFieldMapstruct INSTANCE = Mappers.getMapper(SmartFormRecordFieldMapstruct.class);

    SmartFormRecordFieldMO mvo_CopyTo_MO(SmartFormRecordFieldMVO mvo);
}
