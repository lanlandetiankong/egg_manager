package com.egg.manager.persistence.mongo.mvo.forms;

import lombok.Builder;
import lombok.Data;

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
public class SmartFormRecordMVO {
    /**
     * 表单定义
     */
    private SmartFormDefinitionMVO formDefinition;

}
