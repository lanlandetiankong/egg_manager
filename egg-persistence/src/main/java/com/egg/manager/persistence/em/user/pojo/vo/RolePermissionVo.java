package com.egg.manager.persistence.em.user.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
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
