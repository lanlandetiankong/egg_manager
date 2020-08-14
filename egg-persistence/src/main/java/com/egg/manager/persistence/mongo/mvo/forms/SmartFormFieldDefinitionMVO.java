package com.egg.manager.persistence.mongo.mvo.forms;

import com.egg.manager.persistence.mongo.mvo.MyBaseModelMVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * \* note: 表单类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:06
 * \* Description:
 * \
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartFormFieldDefinitionMVO extends MyBaseModelMVO<String> {

    /**
     * 类型名
     */
    private String fieldName;
    private Boolean isRequired;
    /**
     * 字段类型
     */
    private SmartFormFieldTypeDefinitionMVO formFieldType;

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
