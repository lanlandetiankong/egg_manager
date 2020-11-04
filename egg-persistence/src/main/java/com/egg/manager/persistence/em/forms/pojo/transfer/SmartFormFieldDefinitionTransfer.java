package com.egg.manager.persistence.em.forms.pojo.transfer;

import com.egg.manager.persistence.em.forms.pojo.mapstruct.imap.SmartFormFieldDefinitionMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;


/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("smartFormFieldDefinitionTransfer")
public class SmartFormFieldDefinitionTransfer {

    static SmartFormFieldDefinitionMapstruct smartFormFieldDefinitionMapstruct = SmartFormFieldDefinitionMapstruct.INSTANCE;


}
