package com.egg.manager.facade.persistence.em.logs.pojo.transfer;

import com.egg.manager.facade.persistence.em.logs.pojo.mapstruct.imap.EmPcWebOperationLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 操作日志表-Transfer
 * @date 2020/10/20
 */
@Component
@Named("emPcWebOperationLogTransfer")
public class EmPcWebOperationLogTransfer {

    static EmPcWebOperationLogMapstruct pcWebOperationLogMapstruct = EmPcWebOperationLogMapstruct.INSTANCE;

}
