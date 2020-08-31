package com.egg.manager.persistence.pojo.dto.mysql.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import com.egg.manager.persistence.pojo.dto.mysql.MyBaseMysqlDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDepartmentMysqlDto extends MyBaseMysqlDto {
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
