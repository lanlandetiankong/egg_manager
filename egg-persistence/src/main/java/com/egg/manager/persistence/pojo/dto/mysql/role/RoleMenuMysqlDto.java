package com.egg.manager.persistence.pojo.dto.mysql.role;

import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuMysqlDto extends MyBaseMysqlDto {

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
