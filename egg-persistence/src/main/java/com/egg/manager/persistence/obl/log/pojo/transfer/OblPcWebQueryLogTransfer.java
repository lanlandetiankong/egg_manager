package com.egg.manager.persistence.obl.log.pojo.transfer;

import com.egg.manager.persistence.obl.log.pojo.mapstruct.imap.OblPcWebQueryLogMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description OolongBlog-查询日志表-Transfer
 * @date 2020/10/20
 */
@Component
@Named("oblPcWebQueryLogTransfer")
public class OblPcWebQueryLogTransfer {

    static OblPcWebQueryLogMapstruct oblPcWebQueryLogMapstruct = OblPcWebQueryLogMapstruct.INSTANCE;

}
