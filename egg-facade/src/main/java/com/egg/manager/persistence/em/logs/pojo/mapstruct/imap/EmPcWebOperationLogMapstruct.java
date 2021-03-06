package com.egg.manager.persistence.em.logs.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebOperationLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.pojo.mapstruct.conversion.EmPcWebQueryLogConversion;
import com.egg.manager.persistence.em.logs.pojo.mvo.EmPcWebOperationLogMgvo;
import com.egg.manager.persistence.em.logs.pojo.mvo.EmPcWebQueryLogMgvo;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description 操作日志表-Mapstruct
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmPcWebQueryLogConversion.class}
)
public interface EmPcWebOperationLogMapstruct extends MyBaseMongoMapstruct<EmPcWebOperationLogMgo, EmPcWebOperationLogMgvo> {

    EmPcWebOperationLogMapstruct INSTANCE = Mappers.getMapper(EmPcWebOperationLogMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    EmPcWebQueryLogMgo translateMgvoToMgo(EmPcWebQueryLogMgvo mgvo);
}
