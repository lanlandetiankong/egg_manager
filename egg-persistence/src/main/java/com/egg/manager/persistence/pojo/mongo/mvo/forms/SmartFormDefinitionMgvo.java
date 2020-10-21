package com.egg.manager.persistence.pojo.mongo.mvo.forms;

import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @version V1.0
 * @description:
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormDefinitionMgvo extends BaseModelMgvo<String> {

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
