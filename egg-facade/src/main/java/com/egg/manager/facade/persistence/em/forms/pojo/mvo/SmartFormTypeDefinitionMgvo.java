package com.egg.manager.facade.persistence.em.forms.pojo.mvo;

import com.egg.manager.facade.persistence.exchange.pojo.mongo.mvo.BaseModelMgvo;
import lombok.*;

/**
 * @author zhoucj
 * @description 表单类型
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SmartFormTypeDefinitionMgvo extends BaseModelMgvo<String> {
    private static final long serialVersionUID = -3785014483811369428L;
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
