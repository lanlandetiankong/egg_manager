package com.egg.manager.persistence.pojo.mysql.vo.user;

import com.egg.manager.persistence.pojo.mysql.vo.MyBaseMysqlVo;
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
public class UserRoleVo extends MyBaseMysqlVo {
    /**
     * 账号id
     */
    private String userAccountId;
    /**
     * 角色id
     */
    private String defineRoleId;
    /**
     * 类型
     */
    private Integer type;

}
