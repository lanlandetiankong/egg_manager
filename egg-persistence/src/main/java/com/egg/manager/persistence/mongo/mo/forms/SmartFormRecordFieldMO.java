package com.egg.manager.persistence.mongo.mo.forms;

import com.egg.manager.persistence.mongo.mo.BaseModelMO;
import lombok.*;

/**
 * \* note:表单项
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:58
 * \* Description:
 * \
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormRecordFieldMO {
    /**
     * 表单record
     */
    private SmartFormRecordMO formRecordMO ;
    /**
     * 表单定义
     */
    private SmartFormFieldDefinitionMO formFieldDefinition ;
    /**
     * 字段对应的值
     */
    private Object value;
    
}
