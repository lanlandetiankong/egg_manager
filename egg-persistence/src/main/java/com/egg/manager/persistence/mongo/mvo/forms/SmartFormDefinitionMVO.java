package com.egg.manager.persistence.mongo.mvo.forms;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 22:35
 * \* Description:
 * \
 */

@Data
@Builder
public class SmartFormDefinitionMVO{

    /**
     * 表单名称
     */
    private String name ;
    /**
     * 表单标题
     */
    private String title ;
    /**
     * 表单类型
     */
    private SmartFormTypeDefinitionMVO formType ;
    /**
     * 表单描述
     */
    private String description ;
    /**
     * 备注
     */
    private String remark ;


    /**
     * 表单类型
     */
    private String formTypeId ;


}
