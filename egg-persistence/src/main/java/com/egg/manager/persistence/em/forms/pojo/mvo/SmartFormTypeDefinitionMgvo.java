package com.egg.manager.persistence.em.forms.pojo.mvo;

import com.egg.manager.persistence.expand.pojo.mongo.mvo.BaseModelMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description: 表单类型
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormTypeDefinitionMgvo extends BaseModelMgvo<Long> {
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
