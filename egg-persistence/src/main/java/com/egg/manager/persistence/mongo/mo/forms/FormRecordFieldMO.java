package com.egg.manager.persistence.mongo.mo.forms;

import com.egg.manager.persistence.mongo.mo.BaseModelMO;

/**
 * \* note:表单项
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:58
 * \* Description:
 * \
 */
public class FormRecordFieldMO extends BaseModelMO {
    /**
     * 表单record
     */
    private FormRecordMO formRecordMO ;
    /**
     * 表单定义
     */
    private FormFieldDefinitionMO formFieldDefinition ;
    /**
     * 字段对应的值
     */
    private Object value;
    
}
