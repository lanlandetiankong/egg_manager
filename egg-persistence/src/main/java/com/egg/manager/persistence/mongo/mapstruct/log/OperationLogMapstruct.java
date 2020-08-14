package com.egg.manager.persistence.mongo.mapstruct.log;

import com.egg.manager.persistence.mongo.mo.log.OperationLogMO;
import org.mapstruct.Mapper;

/**
 * <p>
 * 操作日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie123
 * @since 2020-08-11
 */
@Mapper(componentModel = "spring")
public interface OperationLogMapstruct {

    OperationLogMO mvo_CopyTo_MO(OperationLogMapstruct mvo);
}
