package com.egg.manager.persistence.em.logs.pojo.transfer.pc.web;

import com.egg.manager.persistence.em.logs.pojo.mapstruct.imap.pc.web.PcWebQueryLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:查询日志表
 * @date 2020/10/20
 */
@Component
@Named("operationLogTransfer")
public class PcWebQueryLogTransfer {

    static PcWebQueryLogMapstruct pcWebQueryLogMapstruct = PcWebQueryLogMapstruct.INSTANCE;

}
