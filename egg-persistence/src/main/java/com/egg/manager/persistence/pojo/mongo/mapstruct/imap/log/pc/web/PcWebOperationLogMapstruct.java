package com.egg.manager.persistence.pojo.mongo.mapstruct.imap.log.pc.web;

import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebOperationLogMO;
import com.egg.manager.persistence.db.mongo.mo.log.pc.web.PcWebQueryLogMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.conversion.log.pc.web.PcWebQueryLogConversion;
import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.baseExtend.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.log.pc.web.PcWebOperationLogMVO;
import com.egg.manager.persistence.pojo.mongo.mvo.log.pc.web.PcWebQueryLogMVO;
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
public interface PcWebOperationLogMapstruct extends MyBaseMongoMapstruct<PcWebOperationLogMO, PcWebOperationLogMVO> {

    PcWebOperationLogMapstruct INSTANCE = Mappers.getMapper(PcWebOperationLogMapstruct.class);

    @Mappings({})
    PcWebQueryLogMO translateMvoToMo(PcWebQueryLogMVO mvo);
}
