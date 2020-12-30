package com.egg.manager.persistence.em.forms.pojo.mvo;

import com.egg.manager.persistence.exchange.pojo.mongo.mvo.BaseModelMgvo;
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
public class SmartFormRecordMgvo extends BaseModelMgvo<String> {
    private static final long serialVersionUID = -3218502149988627731L;
    /**
     * 表单定义
     */
    private SmartFormDefinitionMgvo formDefinition;

}
