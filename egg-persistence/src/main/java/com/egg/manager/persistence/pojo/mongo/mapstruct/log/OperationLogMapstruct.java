package com.egg.manager.persistence.pojo.mongo.mapstruct.log;

import com.egg.manager.persistence.db.mongo.mo.log.OperationLogMO;
import com.egg.manager.persistence.pojo.mongo.mapstruct.baseExtend.MyBaseMongoMapstruct;
import com.egg.manager.persistence.pojo.mongo.mvo.log.OperationLogMVO;
import com.egg.manager.persistence.pojo.mongo.transfer.log.OperationLogTransfer;
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
        uses = {OperationLogTransfer.class})
public interface OperationLogMapstruct extends MyBaseMongoMapstruct<OperationLogMO, OperationLogMVO> {

    OperationLogMapstruct INSTANCE = Mappers.getMapper(OperationLogMapstruct.class);

    @Mappings({})
    OperationLogMO translateMvoToMo(OperationLogMVO mvo);
}
