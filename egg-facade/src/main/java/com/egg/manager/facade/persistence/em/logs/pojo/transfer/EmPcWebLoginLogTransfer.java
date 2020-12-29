package com.egg.manager.facade.persistence.em.logs.pojo.transfer;

import com.egg.manager.facade.persistence.em.logs.pojo.mapstruct.imap.EmPcWebLoginLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description 登录日志表-Transfer
 * @date 2020/10/20
 */
@Component
@Named("emPcWebLoginLogTransfer")
public class EmPcWebLoginLogTransfer {

    static EmPcWebLoginLogMapstruct pcWebLoginLogMapstruct = EmPcWebLoginLogMapstruct.INSTANCE;


}
