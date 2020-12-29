package com.egg.manager.facade.persistence.em.user.pojo.dto;

import com.egg.manager.facade.persistence.exchange.pojo.mysql.dto.MyBaseMysqlDto;
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
public class EmUserTenantDto extends MyBaseMysqlDto {
    private static final long serialVersionUID = -5743823770303087994L;
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
