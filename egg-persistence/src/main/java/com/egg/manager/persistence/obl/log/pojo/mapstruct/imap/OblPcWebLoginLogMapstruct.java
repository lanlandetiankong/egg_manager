package com.egg.manager.persistence.obl.log.pojo.mapstruct.imap;

import com.egg.manager.persistence.em.logs.pojo.mapstruct.conversion.pc.web.EmPcWebQueryLogConversion;
import com.egg.manager.persistence.em.logs.pojo.mvo.pc.web.EmPcWebLoginLogMgvo;
import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebLoginLogMgo;
import com.egg.manager.persistence.obl.log.pojo.mvo.OblPcWebQueryLogMgvo;
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
        uses = {EmPcWebQueryLogConversion.class}
)
public interface OblPcWebLoginLogMapstruct extends MyBaseMongoMapstruct<OblPcWebLoginLogMgo, EmPcWebLoginLogMgvo> {

    OblPcWebLoginLogMapstruct INSTANCE = Mappers.getMapper(OblPcWebLoginLogMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    OblPcWebLoginLogMgo translateMgvoToMgo(OblPcWebQueryLogMgvo mgvo);
}
