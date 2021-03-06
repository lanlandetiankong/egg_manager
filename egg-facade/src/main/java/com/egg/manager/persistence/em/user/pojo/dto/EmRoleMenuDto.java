package com.egg.manager.persistence.em.user.pojo.dto;

import com.egg.manager.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.*;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmRoleMenuDto extends MyBaseMysqlDto {

    private static final long serialVersionUID = -545897672256570611L;
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
