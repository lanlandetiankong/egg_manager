package com.egg.manager.persistence.pojo.mysql.dto.user;

import com.egg.manager.persistence.pojo.mysql.dto.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDto extends MyBaseMysqlDto {
    /**
     * 账号id
     */
    private String userAccountId;
    /**
     * 角色id
     */
    private String defineRoleId;
    /**
     * 类型
     */
    private Integer type;

}