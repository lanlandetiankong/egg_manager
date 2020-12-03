package com.egg.manager.persistence.em.logs.pojo.transfer;

import com.egg.manager.persistence.em.logs.pojo.mapstruct.imap.EmPcWebQueryLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 查询日志表-Transfer
 * @date 2020/10/20
 */
@Component
@Named("emPcWebQueryLogTransfer")
public class EmPcWebQueryLogTransfer {

    static EmPcWebQueryLogMapstruct emPcWebQueryLogMapstruct = EmPcWebQueryLogMapstruct.INSTANCE;

}
