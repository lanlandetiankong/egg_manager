package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.forms.SmartFormRecordConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.baseExtend.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormRecordMVO;
import org.mapstruct.Mapper;
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
        uses = {SmartFormRecordConversion.class})
public interface SmartFormRecordMapstruct  extends MyBaseMongoMapstruct<SmartFormRecordMO,SmartFormRecordMVO> {

    SmartFormRecordMapstruct INSTANCE = Mappers.getMapper(SmartFormRecordMapstruct.class);

    @Mappings({
    })
    SmartFormRecordMO translateMvoToMo(SmartFormRecordMVO mvo);

}
