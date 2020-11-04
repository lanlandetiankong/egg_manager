package com.egg.manager.persistence.em.forms.pojo.mvo;

import com.egg.manager.persistence.expand.pojo.mongo.mvo.BaseModelMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormFieldTypeDefinitionMgvo extends BaseModelMgvo<Long> {
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
