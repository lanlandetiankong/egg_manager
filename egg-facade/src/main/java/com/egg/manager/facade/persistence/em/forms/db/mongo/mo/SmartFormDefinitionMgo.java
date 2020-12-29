package com.egg.manager.facade.persistence.em.forms.db.mongo.mo;

import com.egg.manager.facade.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "smart_form_definition")
@CompoundIndexes({
        @CompoundIndex(name = "weights_idx", def = "{'weights': 1}")
})
public class SmartFormDefinitionMgo extends MyBaseModelMgo<String> {

    private static final long serialVersionUID = -3978534704489937334L;
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
    private SmartFormTypeDefinitionMgo formType;
    /**
     * 表单描述
     */
    private String description;


}
