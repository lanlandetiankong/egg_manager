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
public class UserGroupVo extends MyBaseMysqlVo {

    /**
     * 组织id
     */
    private String defineGroupId;
    /**
     * 账号id
     */
    private Long userAccountId;
    /**
     * 类型
     */
    private String type;

}
