package com.egg.manager.persistence.em.forms.db.mongo.mo;

import com.egg.manager.persistence.exchange.db.mongo.mo.MyBaseModelMgo;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhoucj
 * @description 表单记录
 * @date 2020/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Document(collection = "smart_form_record")
@CompoundIndexes({
        @CompoundIndex(name = "orderNum_idx", def = "{'orderNum': 1}")
})
public class SmartFormRecordMgo extends MyBaseModelMgo<String> {
    /**
     * 表单定义
     */
    private SmartFormDefinitionMgo formDefinition;

}
