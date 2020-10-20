package com.egg.manager.persistence.db.mongo.mo.forms;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMgo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* note:表单项
 * @author: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:58
 * \* Description:
 * \
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormRecordFieldMgo extends MyBaseModelMgo<String> {
    /**
     * 表单record
     */
    private SmartFormRecordMgo formRecordMO;
    /**
     * 表单定义
     */
    private SmartFormFieldDefinitionMgo formFieldDefinition;
    /**
     * 字段对应的值
     */
    private Object value;

}
