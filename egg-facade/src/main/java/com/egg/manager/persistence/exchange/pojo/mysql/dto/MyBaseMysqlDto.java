package com.egg.manager.persistence.exchange.pojo.mysql.dto;

import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserAccountEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description Dto 继承类(后续可在此处添加通用功能)
 * @date 2020/10/20
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
     * 创建人
     */
    private EmUserAccountEntity createUser;
    /**
     * 最后更新人
     */
    private EmUserAccountEntity lastModifyer;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 是否已经删除，0:否 1:是
     */
    private short isDeleted;
    /**
     * 数据删除时间
     */
    private Date deletedTime;


}
