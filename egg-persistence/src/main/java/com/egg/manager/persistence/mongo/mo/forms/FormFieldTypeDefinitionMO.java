package com.egg.manager.persistence.mongo.mo.forms;

import com.egg.manager.persistence.mongo.mo.BaseModelMO;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * \* note: 表单 字段类型
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:23
 * \* Description:
 * \
 */
@Data
@Builder
@Document(collection = "form_field_type_definition")
@CompoundIndexes({
        @CompoundIndex(name = "orderNum_idx", def = "{'orderNum': 1}")
})
public class FormFieldTypeDefinitionMO extends BaseModelMO {
    /**
     * 字段类型值
     */
    private Integer value ;
    /**
     * 字段名
     */
    private String name ;
    /**
     * 正则验证
     */
    private String regex ;
}
