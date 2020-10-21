package com.egg.manager.persistence.pojo.mongo.mvo.forms;

import com.egg.manager.persistence.pojo.mongo.mvo.BaseModelMgvo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class SmartFormFieldDefinitionMgvo extends BaseModelMgvo<String> {

    /**
     * 类型名
     */
    private String fieldName;
    private Boolean isRequired;
    /**
     * 字段类型
     */
    private SmartFormFieldTypeDefinitionMgvo formFieldType;

    /**
     * 默认值
     */
    private Object defaultValue;
    /**
     * 提示
     */
    private String prompt;
    /**
     * 可选值
     */
    private List<Object> optionalValueList;

    /**
     * pc端是否可搜索
     */
    private Boolean pcSearchAble;
    /**
     * pc端是否展示
     */
    private Boolean pcShowAble;
    /**
     * 类型描述
     */
    private String description;
    /**
     * 备注
     */
    private String remark;

}
