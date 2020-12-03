package com.egg.manager.persistence.obl.log.pojo.mapstruct.imap;

import com.egg.manager.persistence.exchange.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.obl.log.db.mongo.mo.OblPcWebLoginLogMgo;
import com.egg.manager.persistence.obl.log.pojo.mapstruct.conversion.OblPcWebQueryLogConversion;
import com.egg.manager.persistence.obl.log.pojo.mvo.OblPcWebLoginLogMgvo;
import com.egg.manager.persistence.obl.log.pojo.mvo.OblPcWebQueryLogMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author zhoucj
 * @description OolongBlog-登录日志表-iMapstruct
 * @date 2020/10/21
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {OblPcWebQueryLogConversion.class}
)
public interface OblPcWebLoginLogMapstruct extends MyBaseMongoMapstruct<OblPcWebLoginLogMgo, OblPcWebLoginLogMgvo> {

    OblPcWebLoginLogMapstruct INSTANCE = Mappers.getMapper(OblPcWebLoginLogMapstruct.class);

    /**
     * mgvo转mgo
     * @param mgvo
     * @return
     */
    @Mappings({})
    OblPcWebLoginLogMgo translateMgvoToMgo(OblPcWebQueryLogMgvo mgvo);
}
