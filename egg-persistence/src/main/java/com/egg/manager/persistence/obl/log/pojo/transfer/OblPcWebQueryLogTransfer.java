package com.egg.manager.persistence.obl.log.pojo.transfer;

import com.egg.manager.persistence.obl.log.pojo.mapstruct.imap.OblPcWebQueryLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description查询日志表
 * @date 2020/10/20
 */
@Component
@Named("oblPcWebQueryLogTransfer")
public class OblPcWebQueryLogTransfer {

    static OblPcWebQueryLogMapstruct oblPcWebQueryLogMapstruct = OblPcWebQueryLogMapstruct.INSTANCE;

}
