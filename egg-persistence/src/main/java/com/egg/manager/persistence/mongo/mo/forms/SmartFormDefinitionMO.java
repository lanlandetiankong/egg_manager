package com.egg.manager.persistence.mongo.mo.forms;

import com.egg.manager.persistence.mongo.mo.MyBaseModelMO;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "smart_form_definition")
@CompoundIndexes({
        @CompoundIndex(name = "orderNum_idx", def = "{'orderNum': 1}")
})
public class SmartFormDefinitionMO extends MyBaseModelMO<String> {

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
    @Field(value = "formType")
    private SmartFormTypeDefinitionMO formType;
    /**
     * 表单描述
     */
    private String description;
    /**
     * 备注
     */
    private String remark;


}
