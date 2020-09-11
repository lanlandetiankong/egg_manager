package com.egg.manager.persistence.pojo.mongo.transfer.forms;

import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormRecordFieldMapstruct;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * \* note:表单项
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:58
 * \* Description:
 * \
 */
@Component
@Named("smartFormRecordFieldTransfer")
public class SmartFormRecordFieldTransfer {

    static SmartFormRecordFieldMapstruct smartFormRecordFieldMapstruct = SmartFormRecordFieldMapstruct.INSTANCE ;

}
