package com.egg.manager.persistence.em.user.pojo.vo;

import com.egg.manager.persistence.exchange.pojo.mysql.vo.MyBaseMysqlVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmUserJobVo extends MyBaseMysqlVo {
    private static final long serialVersionUID = -4817169022123026582L;
    /**
     * 账号id
     */
    private String userAccountId;
    /**
     * 职务id
     */
    private String defineJobId;

}
