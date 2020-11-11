package com.egg.manager.persistence.em.logs.pojo.mapstruct.imap.pc.web;

import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.pojo.mapstruct.conversion.pc.web.PcWebQueryLogConversion;
import com.egg.manager.persistence.em.logs.pojo.mvo.pc.web.PcWebOperationLogMgvo;
import com.egg.manager.persistence.em.logs.pojo.mvo.pc.web.PcWebQueryLogMgvo;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description操作日志表
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {PcWebQueryLogConversion.class}
)
public interface PcWebOperationLogMapstruct extends MyBaseMongoMapstruct<PcWebOperationLogMgo, PcWebOperationLogMgvo> {

    PcWebOperationLogMapstruct INSTANCE = Mappers.getMapper(PcWebOperationLogMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    PcWebQueryLogMgo translateMgvoToMgo(PcWebQueryLogMgvo mgvo);
}
