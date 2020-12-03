package com.egg.manager.persistence.em.logs.pojo.transfer.pc.web;

import com.egg.manager.persistence.em.logs.pojo.mapstruct.imap.pc.web.EmPcWebQueryLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description查询日志表
 * @date 2020/10/20
 */
@Component
@Named("emPcWebQueryLogTransfer")
public class EmPcWebQueryLogTransfer {

    static EmPcWebQueryLogMapstruct emPcWebQueryLogMapstruct = EmPcWebQueryLogMapstruct.INSTANCE;

}
