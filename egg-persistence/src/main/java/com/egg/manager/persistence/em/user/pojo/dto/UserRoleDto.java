package com.egg.manager.persistence.em.user.pojo.dto;

import com.egg.manager.persistence.expand.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDto extends MyBaseMysqlDto {
    /**
     * 账号id
     */
    private Long userAccountId;
    /**
     * 角色id
     */
    private String defineRoleId;
    /**
     * 类型
     */
    private Integer type;

}
