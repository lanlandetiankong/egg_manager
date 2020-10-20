package com.egg.manager.persistence.pojo.mongo.transfer.log.pc.web;

import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.log.pc.web.PcWebLoginLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 操作日志表 - MongoDB
 * </p>
 *
 * @author zhouchengjie
 * @since 2020-08-11
 */
@Component
@Named("operationLogTransfer")
public class PcWebLoginLogTransfer {

    static PcWebLoginLogMapstruct pcWebLoginLogMapstruct = PcWebLoginLogMapstruct.INSTANCE;


}
