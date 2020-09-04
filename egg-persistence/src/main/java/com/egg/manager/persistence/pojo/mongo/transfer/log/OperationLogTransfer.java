package com.egg.manager.persistence.pojo.mongo.transfer.log;

import com.egg.manager.persistence.pojo.mongo.mapstruct.log.OperationLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 操作日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie123
 * @since 2020-08-11
 */
@Component
@Named("OperationLogTransfer")
public class OperationLogTransfer {

    static OperationLogMapstruct operationLogMapstruct = OperationLogMapstruct.INSTANCE ;

}
