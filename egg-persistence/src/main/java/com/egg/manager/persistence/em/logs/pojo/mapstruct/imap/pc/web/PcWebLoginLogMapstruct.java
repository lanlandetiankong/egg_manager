package com.egg.manager.persistence.em.logs.pojo.mapstruct.imap.pc.web;

import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebLoginLogMgo;
import com.egg.manager.persistence.em.logs.db.mongo.mo.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.em.logs.pojo.mapstruct.conversion.pc.web.PcWebQueryLogConversion;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.em.logs.pojo.mvo.pc.web.PcWebLoginLogMgvo;
import com.egg.manager.persistence.em.logs.pojo.mvo.pc.web.PcWebQueryLogMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description 登录日志表
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {PcWebQueryLogConversion.class}
)
public interface PcWebLoginLogMapstruct extends MyBaseMongoMapstruct<PcWebLoginLogMgo, PcWebLoginLogMgvo> {

    PcWebLoginLogMapstruct INSTANCE = Mappers.getMapper(PcWebLoginLogMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    PcWebQueryLogMgo translateMgvoToMgo(PcWebQueryLogMgvo mgvo);
}
