package com.egg.manager.persistence.em.user.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
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
