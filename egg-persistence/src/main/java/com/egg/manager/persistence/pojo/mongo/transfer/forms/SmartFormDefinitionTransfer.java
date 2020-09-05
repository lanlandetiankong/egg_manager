package com.egg.manager.persistence.pojo.mongo.transfer.forms;

import com.egg.manager.persistence.pojo.mongo.mapstruct.forms.SmartFormDefinitionMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 22:35
 * \* Description:
 * \
 */
@Component
@Named("smartFormDefinitionTransfer")
public class SmartFormDefinitionTransfer {

    static SmartFormDefinitionMapstruct smartFormDefinitionMapstruct = SmartFormDefinitionMapstruct.INSTANCE ;

}
