package com.egg.manager.persistence.pojo.mongo.transfer.forms;

import com.egg.manager.persistence.pojo.mongo.mapstruct.forms.SmartFormTypeDefinitionMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * \* note: 表单类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:06
 * \* Description:
 * \
 */
@Component
@Named("smartFormTypeDefinitionTransfer")
public class SmartFormTypeDefinitionTransfer {

    static SmartFormTypeDefinitionMapstruct smartFormTypeDefinitionMapstruct = SmartFormTypeDefinitionMapstruct.INSTANCE ;

}
