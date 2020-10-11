package com.egg.manager.persistence.db.mongo.mo.forms;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMgo;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

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
@EqualsAndHashCode(callSuper = true)
@Document(collection = "smart_form_type_definition")
@CompoundIndexes({
        @CompoundIndex(name = "orderNum_idx", def = "{'orderNum': 1}")
})
public class SmartFormTypeDefinitionMgo extends MyBaseModelMgo<String> {
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