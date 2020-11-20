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
