package com.egg.manager.persistence.pojo.vo.mysql.role;

import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuMysqlVo extends MyBaseMysqlVo {

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
