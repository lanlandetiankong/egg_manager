package com.egg.manager.persistence.pojo.mongo.mvo.forms;

import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* note: 表单类型
 * @author: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:06
 * \* Description:
 * \
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormTypeDefinitionMgvo extends BaseModelMgvo<String> {
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
