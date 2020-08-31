package com.egg.manager.persistence.pojo.dto.mysql;

import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Dto 继承类(后续可在此处添加通用功能)
 */
@Data
public class MyBaseMysqlDto implements Serializable {

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
    private UserAccount createUser;
    /**
     * 最后更新人-vo
     */
    private UserAccount lastModifyer;
}
