package com.egg.manager.persistence.em.forms.db.mongo.mo;

import com.egg.manager.persistence.enhance.db.mongo.mo.MyBaseModelMgo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description: 表单项
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormRecordFieldMgo extends MyBaseModelMgo<Long> {
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
