package com.egg.manager.persistence.em.forms.db.mongo.mo;

import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
@Document(collection = "smart_form_field_definition")
@CompoundIndexes({
        @CompoundIndex(name = "orderNum_idx", def = "{'orderNum': 1}")
})
public class SmartFormFieldDefinitionMgo extends MyBaseModelMgo<Long> {

    /**
     * 类型名
     */
    private String fieldName;
    private Boolean isRequired;
    /**
     * 字段类型
     */
    private SmartFormFieldTypeDefinitionMgo formFieldType;

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

}
