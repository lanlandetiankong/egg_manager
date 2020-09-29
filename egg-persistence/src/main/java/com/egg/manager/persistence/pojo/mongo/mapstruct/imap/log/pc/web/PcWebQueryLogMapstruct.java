package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMgo;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.log.pc.web.PcWebQueryLogConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.fundamental.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.log.pc.web.PcWebQueryLogMgvo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * 操作日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie123
 * @since 2020-08-11
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {PcWebQueryLogConversion.class}
)
public interface PcWebQueryLogMapstruct extends MyBaseMongoMapstruct<PcWebQueryLogMgo, PcWebQueryLogMgvo> {

    PcWebQueryLogMapstruct INSTANCE = Mappers.getMapper(PcWebQueryLogMapstruct.class);

    @Mappings({})
    PcWebQueryLogMgo translateMvoToMo(PcWebQueryLogMgvo mvo);
}
