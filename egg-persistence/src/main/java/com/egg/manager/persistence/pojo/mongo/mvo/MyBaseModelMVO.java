package com.egg.manager.persistence.pojo.mongo.mvo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MyBaseModelMVO<K> implements Serializable {
    private K fid;
    /**
     * 顺序
     */
    private Integer orderNum;
    /**
     * 状态
     */
    private Short status;
    private Integer version;

    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 创建人名称
     */
    private String createUserNickName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新用户id
     */
    private String lastModifyerId;
    /**
     * 最后更新用户名称
     */
    private String lastModifyerNickName;
    /**
     * 最后更新时间
     */
    private Date lastModifiedDate;
}
