package com.egg.manager.persistence.pojo.vo.mysql.user;

import com.egg.manager.persistence.pojo.vo.mysql.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
