package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordMgo;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.forms.SmartFormRecordConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormRecordMgvo;
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
public interface SmartFormRecordMapstruct extends MyBaseMongoMapstruct<SmartFormRecordMgo, SmartFormRecordMgvo> {

    SmartFormRecordMapstruct INSTANCE = Mappers.getMapper(SmartFormRecordMapstruct.class);

    @Mappings({
    })
    SmartFormRecordMgo translateMvoToMo(SmartFormRecordMgvo mvo);

}
