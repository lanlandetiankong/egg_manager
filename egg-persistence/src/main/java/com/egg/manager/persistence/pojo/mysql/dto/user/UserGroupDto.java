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
public class UserGroupDto extends MyBaseMysqlDto {
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
