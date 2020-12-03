package com.egg.manager.persistence.em.logs.pojo.transfer.pc.web;

import com.egg.manager.persistence.em.logs.pojo.mapstruct.imap.pc.web.EmPcWebOperationLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description操作日志表
 * @date 2020/10/20
 */
@Component
@Named("emPcWebOperationLogTransfer")
public class EmPcWebOperationLogTransfer {

    static EmPcWebOperationLogMapstruct pcWebOperationLogMapstruct = EmPcWebOperationLogMapstruct.INSTANCE;

}
