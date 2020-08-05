package com.egg.manager.persistence.mongo.mo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Date: 2020/7/22
 * \* Time: 23:15
 * \* Description:
 * \
 */
@Data
public class BaseModelMO<K> implements Serializable {

    @Id
    private K fid;

    /**
     * 顺序
     */
    @Field(value = "orderNum")
    private Integer orderNum;
    /**
     * 状态
     */
    @Field(value = "status")
    private Short status;
    @Version
    private Integer version;

    /**
     * 创建人id
     */
    @Field(value = "createUserId")
    private String createUserId;
    /**
     * 创建人名称
     */
    private String createUserNickName;
    /**
     * 创建时间
     */
    @CreatedDate
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
    @LastModifiedDate
    private Date lastModifiedDate;
}
