package com.egg.manager.persistence.em.forms.pojo.mvo;

import com.egg.manager.persistence.exchange.pojo.mongo.mvo.BaseModelMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormRecordMgvo extends BaseModelMgvo<String> {
    /**
     * 表单定义
     */
    private SmartFormDefinitionMgvo formDefinition;

}
