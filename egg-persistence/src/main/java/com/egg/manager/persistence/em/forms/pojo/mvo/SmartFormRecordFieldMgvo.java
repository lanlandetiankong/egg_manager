package com.egg.manager.persistence.em.forms.pojo.mvo;

import com.egg.manager.persistence.exchange.pojo.mongo.mvo.BaseModelMgvo;
import lombok.*;

/**
 * @author zhoucj
 * @description表单项
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SmartFormRecordFieldMgvo extends BaseModelMgvo<String> {
    /**
     * 表单record
     */
    private SmartFormRecordMgvo formRecord;
    /**
     * 表单定义
     */
    private SmartFormFieldDefinitionMgvo formFieldDefinition;
    /**
     * 字段对应的值
     */
    private Object value;

}
