package com.egg.manager.facade.persistence.em.forms.pojo.mvo;

import com.egg.manager.facade.persistence.exchange.pojo.mongo.mvo.BaseModelMgvo;
import lombok.*;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SmartFormFieldTypeDefinitionMgvo extends BaseModelMgvo<String> {
    private static final long serialVersionUID = -3648524431495029395L;
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
