package com.egg.manager.facade.persistence.em.logs.pojo.mapstruct.imap;

import com.egg.manager.facade.persistence.em.logs.db.mongo.mo.EmPcWebLoginLogMgo;
import com.egg.manager.facade.persistence.em.logs.db.mongo.mo.EmPcWebQueryLogMgo;
import com.egg.manager.facade.persistence.em.logs.pojo.mapstruct.conversion.EmPcWebQueryLogConversion;
import com.egg.manager.facade.persistence.em.logs.pojo.mvo.EmPcWebLoginLogMgvo;
import com.egg.manager.facade.persistence.em.logs.pojo.mvo.EmPcWebQueryLogMgvo;
import com.egg.manager.facade.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description 登录日志表-Mapstruct
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {EmPcWebQueryLogConversion.class}
)
public interface EmPcWebLoginLogMapstruct extends MyBaseMongoMapstruct<EmPcWebLoginLogMgo, EmPcWebLoginLogMgvo> {

    EmPcWebLoginLogMapstruct INSTANCE = Mappers.getMapper(EmPcWebLoginLogMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    EmPcWebQueryLogMgo translateMgvoToMgo(EmPcWebQueryLogMgvo mgvo);
}
