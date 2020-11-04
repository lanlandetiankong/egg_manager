package com.egg.manager.persistence.em.forms.pojo.transfer;

import com.egg.manager.persistence.em.forms.pojo.mapstruct.imap.SmartFormRecordFieldMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * @author zhoucj
 * @description:表单项
 * @date 2020/10/20
 */
@Component
@Named("smartFormRecordFieldTransfer")
public class SmartFormRecordFieldTransfer {

    static SmartFormRecordFieldMapstruct smartFormRecordFieldMapstruct = SmartFormRecordFieldMapstruct.INSTANCE;

}
