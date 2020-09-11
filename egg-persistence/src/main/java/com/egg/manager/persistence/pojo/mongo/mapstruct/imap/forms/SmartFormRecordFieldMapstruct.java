package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordFieldMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.forms.SmartFormRecordFieldConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.baseExtend.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormRecordFieldMVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * \* note:表单项
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:58
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormRecordFieldConversion.class})
public interface SmartFormRecordFieldMapstruct  extends MyBaseMongoMapstruct<SmartFormRecordFieldMO,SmartFormRecordFieldMVO> {
    SmartFormRecordFieldMapstruct INSTANCE = Mappers.getMapper(SmartFormRecordFieldMapstruct.class);

    @Mappings({
            @Mapping(target = "formRecordMO",expression = "java(commonTranslateSmartFormRecordMVOToMO(mvo.getFormRecord()))")
    })
    SmartFormRecordFieldMO translateMvoToMo(SmartFormRecordFieldMVO mvo);
}
