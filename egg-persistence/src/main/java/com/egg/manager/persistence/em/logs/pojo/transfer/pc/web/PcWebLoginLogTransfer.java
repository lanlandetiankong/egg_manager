package com.egg.manager.persistence.em.logs.pojo.transfer.pc.web;

import com.egg.manager.persistence.em.logs.pojo.mapstruct.imap.pc.web.PcWebLoginLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description登录日志表
 * @date 2020/10/20
 */
@Component
@Named("operationLogTransfer")
public class PcWebLoginLogTransfer {

    static PcWebLoginLogMapstruct pcWebLoginLogMapstruct = PcWebLoginLogMapstruct.INSTANCE;


}
