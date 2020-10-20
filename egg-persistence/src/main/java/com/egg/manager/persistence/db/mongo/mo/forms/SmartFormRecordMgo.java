package com.egg.manager.persistence.db.mongo.mo.forms;

import com.egg.manager.persistence.db.mongo.mo.MyBaseModelMgo;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * \* note:表单项
 * @author: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:58
 * \* Description:
 * \
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
