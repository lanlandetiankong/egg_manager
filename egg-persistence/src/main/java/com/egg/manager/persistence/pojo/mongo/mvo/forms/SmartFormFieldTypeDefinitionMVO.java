package com.egg.manager.persistence.pojo.mongo.mvo.forms;

import com.egg.manager.persistence.pojo.mongo.mvo.MyBaseModelMVO;
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
public class SmartFormFieldTypeDefinitionMVO extends MyBaseModelMVO<String> {
    /**
     * 字段类型值
     */
    private Integer value;
    /**
     * 字段名
     */
    private String name;
    /**
     * 正则验证
     */
    private String regex;
}