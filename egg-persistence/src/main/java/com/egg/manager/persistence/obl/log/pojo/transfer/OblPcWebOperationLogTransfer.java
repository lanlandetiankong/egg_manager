package com.egg.manager.persistence.obl.log.pojo.transfer;

import com.egg.manager.persistence.obl.log.pojo.mapstruct.imap.OblPcWebOperationLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description OolongBlog-操作日志表-Transfer
 * @date 2020/10/20
 */
@Component
@Named("oblPcWebOperationLogTransfer")
public class OblPcWebOperationLogTransfer {

    static OblPcWebOperationLogMapstruct oblPcWebOperationLogMapstruct = OblPcWebOperationLogMapstruct.INSTANCE;

}
