package com.egg.manager.persistence.em.forms.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormRecordFieldMgo;
import com.egg.manager.persistence.em.forms.pojo.mapstruct.conversion.SmartFormRecordFieldConversion;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.em.forms.pojo.mvo.SmartFormRecordFieldMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:表单项
 * @date 2020/10/21
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
            @Mapping(target = "formRecordMO", expression = "java(commonTranslateSmartFormRecordMgvoToMgo(mgvo.getFormRecord()))")
    })
    SmartFormRecordFieldMgo translateMgvoToMgo(SmartFormRecordFieldMgvo mgvo);
}
