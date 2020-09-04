package com.egg.manager.persistence.pojo.mysql.vo.user;

import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJobVo extends MyBaseMysqlVo {
    /**
     * 账号id
     */
    private String userAccountId;
    /**
     * 职务id
     */
    private String defineJobId;

}
