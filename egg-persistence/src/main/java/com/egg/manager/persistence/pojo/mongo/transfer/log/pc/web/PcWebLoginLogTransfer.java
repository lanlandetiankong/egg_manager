package com.egg.manager.persistence.pojo.mongo.transfer.log.pc.web;

import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.log.pc.web.PcWebLoginLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:登录日志表
 * @date 2020/10/20
 */
@Component
@Named("operationLogTransfer")
public class PcWebLoginLogTransfer {

    static PcWebLoginLogMapstruct pcWebLoginLogMapstruct = PcWebLoginLogMapstruct.INSTANCE;


}
