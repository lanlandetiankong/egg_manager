package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMgo;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.log.pc.web.PcWebQueryLogConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.log.pc.web.PcWebOperationLogMgvo;
import com.egg.manager.persistence.pojo.mongo.mvo.log.pc.web.PcWebQueryLogMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description:操作日志表
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
