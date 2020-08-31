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
public class UserGroupMysqlDto extends MyBaseMysqlDto {
    /**
     * 组织id
     */
    private String defineGroupId;
    /**
     * 账号id
     */
    private String userAccountId;
    /**
     * 类型
     */
    private String type;

}
