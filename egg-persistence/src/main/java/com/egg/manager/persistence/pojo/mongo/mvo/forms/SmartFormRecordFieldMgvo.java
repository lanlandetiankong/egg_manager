package com.egg.manager.persistence.pojo.mongo.mvo.forms;

import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description:表单项
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
