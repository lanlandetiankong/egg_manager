package com.egg.manager.persistence.pojo.mysql.vo.user;

import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupVo extends MyBaseMysqlVo {

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
