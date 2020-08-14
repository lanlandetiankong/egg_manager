package com.egg.manager.persistence.mongo.mvo.forms;

import com.egg.manager.persistence.mongo.mvo.MyBaseModelMVO;
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
public class SmartFormRecordMVO extends MyBaseModelMVO<String> {
    /**
     * 表单定义
     */
    private SmartFormDefinitionMVO formDefinition;

}
