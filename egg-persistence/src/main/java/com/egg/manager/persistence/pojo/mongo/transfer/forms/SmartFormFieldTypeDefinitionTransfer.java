package com.egg.manager.persistence.pojo.mongo.transfer.forms;

import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormFieldTypeDefinitionMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * \* note: 表单 字段类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:23
 * \* Description:
 * \
 */
@Component
@Named("smartFormFieldTypeDefinitionTransfer")
public class SmartFormFieldTypeDefinitionTransfer {

    static SmartFormFieldTypeDefinitionMapstruct smartFormFieldTypeDefinitionMapstruct = SmartFormFieldTypeDefinitionMapstruct.INSTANCE;


}
