package com.egg.manager.persistence.em.forms.db.mongo.mo;

import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "smart_form_type_definition")
@CompoundIndexes({
        @CompoundIndex(name = "weights_idx", def = "{'weights': 1}")
})
public class SmartFormTypeDefinitionMgo extends MyBaseModelMgo<String> {
    private static final long serialVersionUID = 1910831325507308349L;
    /**
     * 类型名
     */
    private String name;
    /**
     * 类型描述
     */
    private String description;

}
