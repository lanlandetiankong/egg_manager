package com.egg.manager.persistence.em.forms.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.forms.db.mongo.mo.SmartFormRecordMgo;
import com.egg.manager.persistence.em.forms.pojo.mapstruct.conversion.SmartFormRecordConversion;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.em.forms.pojo.mvo.SmartFormRecordMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description: 表单项
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {SmartFormRecordConversion.class})
public interface SmartFormRecordMapstruct extends MyBaseMongoMapstruct<SmartFormRecordMgo, SmartFormRecordMgvo> {

    SmartFormRecordMapstruct INSTANCE = Mappers.getMapper(SmartFormRecordMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({
    })
    SmartFormRecordMgo translateMgvoToMgo(SmartFormRecordMgvo mgvo);

}
