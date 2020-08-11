package com.egg.manager.persistence.mongo.mvo.forms;

import lombok.Builder;
import lombok.Data;

/**
 * \* note: 表单类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:06
 * \* Description:
 * \
 */
@Data
@Builder
public class SmartFormTypeDefinitionMVO {
    /**
     * 类型名
     */
    private String name;
    /**
     * 类型描述
     */
    private String description;
    private String remark;

}
