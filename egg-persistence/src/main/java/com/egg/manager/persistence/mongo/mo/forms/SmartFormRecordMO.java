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
@EqualsAndHashCode(callSuper = true)
public class SmartFormRecordMO extends BaseModelMO<String> {
    /**
     * 表单定义
     */
    private SmartFormDefinitionMO formDefinition ;

}
