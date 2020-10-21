package com.egg.manager.persistence.pojo.mongo.transfer.log.pc.web;

import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.log.pc.web.PcWebOperationLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:操作日志表
 * @date 2020/10/20
 */
@Component
@Named("operationLogTransfer")
public class PcWebOperationLogTransfer {

    static PcWebOperationLogMapstruct pcWebOperationLogMapstruct = PcWebOperationLogMapstruct.INSTANCE;

}
