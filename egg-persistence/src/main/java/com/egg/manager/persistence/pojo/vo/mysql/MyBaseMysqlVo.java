package com.egg.manager.persistence.pojo.vo.mysql;

import com.egg.manager.persistence.pojo.vo.mysql.user.UserAccountVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MyBaseMysqlVo implements Serializable {
    private String fid;

    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Short state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建用户id
     */
    private String createUserId;
    /**
     * 最后修改用户id
     */
    private String lastModifyerId;
    /**
     * 创建人-vo
     */
    private UserAccountVo createUser;
    /**
     * 最后更新人-vo
     */
    private UserAccountVo lastModifyer;
}
