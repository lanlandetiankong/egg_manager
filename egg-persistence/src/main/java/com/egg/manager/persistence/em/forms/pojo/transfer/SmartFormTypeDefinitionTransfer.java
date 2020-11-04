package com.egg.manager.persistence.em.forms.pojo.transfer;

import com.egg.manager.persistence.em.forms.pojo.mapstruct.mapstruct.imap.SmartFormTypeDefinitionMapstruct;
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
