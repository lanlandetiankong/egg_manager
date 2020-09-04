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
public class RoleMenuDto extends MyBaseMysqlDto {

    /**
     * 角色id
     */
    private String defineRoleId;
    /**
     * 菜单id
     */
    private String defineMenuId;
    /**
     * 类型
     */
    private Integer type;
}
