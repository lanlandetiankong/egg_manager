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
public class SmartFormDefinitionMgvo extends BaseModelMgvo<String> {

    private static final long serialVersionUID = -5520506618043906484L;
    /**
     * 表单名称
     */
    private String name;
    /**
     * 表单标题
     */
    private String title;
    /**
     * 表单类型
     */
    private SmartFormTypeDefinitionMgvo formType;
    /**
     * 表单描述
     */
    private String description;
    /**
     * 备注
     */
    private String remark;


    /**
     * 表单类型
     */
    private String formTypeId;


}
