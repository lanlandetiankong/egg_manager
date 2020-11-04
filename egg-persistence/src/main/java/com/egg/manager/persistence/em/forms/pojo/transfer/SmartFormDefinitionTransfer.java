package com.egg.manager.persistence.em.forms.pojo.transfer;

import com.egg.manager.persistence.em.forms.pojo.mapstruct.mapstruct.imap.SmartFormDefinitionMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("smartFormDefinitionTransfer")
public class SmartFormDefinitionTransfer {

    static SmartFormDefinitionMapstruct smartFormDefinitionMapstruct = SmartFormDefinitionMapstruct.INSTANCE;

}
