package com.egg.manager.persistence.em.forms.db.mongo.mo;

import com.egg.manager.persistence.enhance.db.mongo.mo.MyBaseModelMgo;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description: 表单类型
 * @date 2020/10/20
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
public class SmartFormTypeDefinitionMgo extends MyBaseModelMgo<Long> {
    /**
     * 类型名
     */
    private String name;
    /**
     * 类型描述
     */
    private String description;

}
