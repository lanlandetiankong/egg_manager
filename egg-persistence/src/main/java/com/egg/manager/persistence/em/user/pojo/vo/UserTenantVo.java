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
@EqualsAndHashCode(callSuper = true)
public class UserTenantVo extends MyBaseMysqlVo {
    /**
     * 账号id
     */
    private String userAccountId;
    /**
     * 租户id
     */
    private String defineTenantId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 是否管理员
     */
    private Short isManager;

}
