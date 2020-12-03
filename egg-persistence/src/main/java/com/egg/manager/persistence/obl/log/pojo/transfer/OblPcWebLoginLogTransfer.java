package com.egg.manager.persistence.obl.log.pojo.transfer;

import com.egg.manager.persistence.obl.log.pojo.mapstruct.imap.OblPcWebLoginLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description登录日志表
 * @date 2020/10/20
 */
@Component
@Named("oblPcWebLoginLogTransfer")
public class OblPcWebLoginLogTransfer {

    static OblPcWebLoginLogMapstruct oblPcWebLoginLogMapstruct = OblPcWebLoginLogMapstruct.INSTANCE;


}
