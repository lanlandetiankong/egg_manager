package com.egg.manager.facade.persistence.exchange.pojo.mysql.vo;

import com.egg.manager.facade.persistence.em.user.pojo.vo.EmUserAccountVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
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
     * 创建人
     */
    private EmUserAccountVo createUser;
    /**
     * 最后更新人
     */
    private EmUserAccountVo lastModifyer;

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
