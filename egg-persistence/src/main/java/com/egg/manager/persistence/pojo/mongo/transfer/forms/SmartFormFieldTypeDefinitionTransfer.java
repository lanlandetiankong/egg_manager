package com.egg.manager.persistence.pojo.mongo.transfer.forms;

import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormFieldTypeDefinitionMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:表单 字段类型
 * @date 2020/10/20
 */
@Component
@Named("smartFormFieldTypeDefinitionTransfer")
public class SmartFormFieldTypeDefinitionTransfer {

    static SmartFormFieldTypeDefinitionMapstruct smartFormFieldTypeDefinitionMapstruct = SmartFormFieldTypeDefinitionMapstruct.INSTANCE;


}
