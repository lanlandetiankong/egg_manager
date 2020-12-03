package com.egg.manager.persistence.obl.log.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.pojo.mapstruct.conversion.pc.web.EmPcWebQueryLogConversion;
import com.egg.manager.persistence.em.logs.pojo.mvo.pc.web.EmPcWebOperationLogMgvo;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebOperationLogMgo;
import com.egg.manager.persistence.obl.log.pojo.mvo.OblPcWebQueryLogMgvo;
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
        uses = {EmPcWebQueryLogConversion.class}
)
public interface OblPcWebOperationLogMapstruct extends MyBaseMongoMapstruct<OblPcWebOperationLogMgo, EmPcWebOperationLogMgvo> {

    OblPcWebOperationLogMapstruct INSTANCE = Mappers.getMapper(OblPcWebOperationLogMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    EmPcWebQueryLogMgo translateMgvoToMgo(OblPcWebQueryLogMgvo mgvo);
}
