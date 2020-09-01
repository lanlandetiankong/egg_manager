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