package com.egg.manager.persistence.em.forms.db.mongo.mo;

import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import lombok.*;

/**
 * @author zhoucj
 * @description 表单 字段类型
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SmartFormFieldTypeDefinitionMgo extends MyBaseModelMgo<String> {
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
