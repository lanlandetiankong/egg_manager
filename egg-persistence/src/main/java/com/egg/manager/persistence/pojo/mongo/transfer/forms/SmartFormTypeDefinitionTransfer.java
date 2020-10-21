package com.egg.manager.persistence.pojo.mongo.transfer.forms;

import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormTypeDefinitionMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:表单类型
 * @date 2020/10/20
 */
@Component
@Named("smartFormTypeDefinitionTransfer")
public class SmartFormTypeDefinitionTransfer {

    static SmartFormTypeDefinitionMapstruct smartFormTypeDefinitionMapstruct = SmartFormTypeDefinitionMapstruct.INSTANCE;

}
