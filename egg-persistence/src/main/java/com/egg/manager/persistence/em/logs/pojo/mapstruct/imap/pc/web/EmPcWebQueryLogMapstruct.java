package com.egg.manager.persistence.em.logs.pojo.mapstruct.imap.pc.web;

import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.pojo.mapstruct.conversion.pc.web.EmPcWebQueryLogConversion;
import com.egg.manager.persistence.em.logs.pojo.mvo.pc.web.EmPcWebQueryLogMgvo;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description 操作日志表
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmPcWebQueryLogConversion.class}
)
public interface EmPcWebQueryLogMapstruct extends MyBaseMongoMapstruct<EmPcWebQueryLogMgo, EmPcWebQueryLogMgvo> {

    EmPcWebQueryLogMapstruct INSTANCE = Mappers.getMapper(EmPcWebQueryLogMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    EmPcWebQueryLogMgo translateMgvoToMgo(EmPcWebQueryLogMgvo mgvo);
}
