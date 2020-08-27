package com.egg.manager.persistence.db.mongo.mvo.forms;

import com.egg.manager.persistence.db.mongo.mvo.MyBaseModelMVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class SmartFormTypeDefinitionMVO extends MyBaseModelMVO<String> {
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
