package com.egg.manager.persistence.mongo.mo.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* note: 表单 字段类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:23
 * \* Description:
 * \
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormFieldTypeDefinitionMO {
    /**
     * 字段类型值
     */
    private Integer value ;
    /**
     * 字段名
     */
    private String name ;
    /**
     * 正则验证
     */
    private String regex ;
}
