package com.egg.manager.persistence.pojo.mongo.transfer.forms;

import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormFieldDefinitionMapstruct;
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
