package com.egg.manager.persistence.em.forms.db.mongo.mo;

import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import lombok.*;

/**
 * @author zhoucj
 * @description 表单项
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SmartFormRecordFieldMgo extends MyBaseModelMgo<String> {
    private static final long serialVersionUID = 2043938032993803265L;
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
