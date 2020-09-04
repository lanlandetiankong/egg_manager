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
public class UserDepartmentDto extends MyBaseMysqlDto {
    /**
     * 用户账号id
     */
    private String userAccountId;
    /**
     * 部门id
     */
    private String defineDepartmentId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 是否部门管理员
     */
    private Short isManager;

}
