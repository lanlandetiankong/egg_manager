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
public class UserGroupVo extends MyBaseMysqlVo {

    private static final long serialVersionUID = -4067980652372880101L;
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
