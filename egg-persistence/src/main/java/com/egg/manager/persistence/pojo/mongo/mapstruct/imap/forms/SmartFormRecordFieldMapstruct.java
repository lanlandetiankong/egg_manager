package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms;

import com.egg.manager.persistence.db.mongo.mo.forms.SmartFormRecordFieldMgo;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.forms.SmartFormRecordFieldConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.forms.SmartFormRecordFieldMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * \* note:表单项
 * @author: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:58
 * \* Description:
 * \
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormRecordFieldConversion.class})
public interface SmartFormRecordFieldMapstruct extends MyBaseMongoMapstruct<SmartFormRecordFieldMgo, SmartFormRecordFieldMgvo> {
    SmartFormRecordFieldMapstruct INSTANCE = Mappers.getMapper(SmartFormRecordFieldMapstruct.class);
    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({
            @Mapping(target = "formRecordMO", expression = "java(commonTranslateSmartFormRecordMVOToMO(mgvo.getFormRecord()))")
    })
    SmartFormRecordFieldMgo translateMgvoToMgo(SmartFormRecordFieldMgvo mgvo);
}
