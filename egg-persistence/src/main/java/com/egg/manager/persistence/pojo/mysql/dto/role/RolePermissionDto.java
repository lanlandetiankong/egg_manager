package com.egg.manager.persistence.pojo.mysql.dto.role;

import com.egg.manager.persistence.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDto extends MyBaseMysqlDto {
    /**
     * 角色id
     */
    private String defineRoleId;
    /**
     * 权限id
     */
    private String definePermissionId;
    /**
     * 类型
     */
    private Integer type;

}