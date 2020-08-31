package com.egg.manager.persistence.pojo.dto.mysql.define;

import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefinePermissionMysqlDto extends MyBaseMysqlDto {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 是否确认发布，发布之后不可修改
     */
    private Short ensure;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 是否确认发布_名称
     */
    private String ensureStr;
    /**
     * 类型_名称
     */
    private String typeStr;



}
