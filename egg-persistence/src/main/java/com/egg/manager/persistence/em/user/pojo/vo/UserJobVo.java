package com.egg.manager.persistence.em.user.pojo.vo;

import com.egg.manager.persistence.enhance.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description:
 * @date 2020/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJobVo extends MyBaseMysqlVo {
    /**
     * 账号id
     */
    private Long userAccountId;
    /**
     * 职务id
     */
    private String defineJobId;

}
