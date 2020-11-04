package com.egg.manager.persistence.em.forms.db.mongo.mo;

import com.egg.manager.persistence.expand.db.mongo.mo.MyBaseModelMgo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description: 表单 字段类型
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormFieldTypeDefinitionMgo extends MyBaseModelMgo<Long> {
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
