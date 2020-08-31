package com.egg.manager.persistence.pojo.dto.mysql.define;

import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineDepartmentMysqlDto extends MyBaseMysqlDto {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 上级id
     */
    private String parentId;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 排序值
     */
    private Integer orderNum;
    /**
     * 描述
     */
    private String description;

    /**
     * 上级部门-vo
     */
    private DefineDepartmentMysqlDto parentDepartment;


}
