package com.egg.manager.persistence.pojo.mysql.dto.organization;

import com.egg.manager.persistence.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineTenantDto extends MyBaseMysqlDto {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 数据库类型 code
     */
    private String dbCode;
    /**
     * 类型_名称
     */
    private String typeStr;
}
