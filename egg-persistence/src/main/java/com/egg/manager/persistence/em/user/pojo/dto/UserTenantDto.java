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
public class UserTenantDto extends MyBaseMysqlDto {
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
