package com.egg.manager.persistence.pojo.mongo.mvo.forms;

import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class SmartFormRecordMgvo extends BaseModelMgvo<String> {
    /**
     * 表单定义
     */
    private SmartFormDefinitionMgvo formDefinition;

}
