package com.egg.manager.persistence.pojo.mongo.transfer.forms;

import com.egg.manager.persistence.pojo.mongo.mapstruct.imap.forms.SmartFormRecordFieldMapstruct;
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
