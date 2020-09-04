package com.egg.manager.persistence.pojo.mysql.vo.role;

import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionVo extends MyBaseMysqlVo {
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
