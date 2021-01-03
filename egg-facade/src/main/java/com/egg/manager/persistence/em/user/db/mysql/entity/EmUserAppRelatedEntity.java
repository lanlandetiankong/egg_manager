package com.egg.manager.persistence.em.user.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description app用户关联表-Entity
 * @date 2020-12-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("em_user_app_related")
public class EmUserAppRelatedEntity extends Model<EmUserAppRelatedEntity> {
    private static final long serialVersionUID = -80563741400821033L;

    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;
    /**
     * 来源用户id
     */
    @TableField("from_user_id")
    private String fromUserId;
    /**
     * 来源平台
     */
    @TableField("from_platform")
    private String fromPlatform;
    /**
     * 关联用户id
     */
    @TableField("related_user_id")
    private String relatedUserId;
    /**
     * 关联平台
     */
    @TableField("related_platform")
    private String relatedPlatform;
    /**
     * 状态值
     */
    @TableField(value = FieldConst.COL_STATE, fill = FieldFill.INSERT)
    private Short state;
    /**
     * 备注
     */
    @TableField(FieldConst.COL_REMARK)
    private String remark;
    /**
     * 版本号
     */
    @Version
    @TableField(value = FieldConst.COL_VERSION)
    private Integer version;
    /**
     * 创建人id
     */
    @TableField(value = FieldConst.COL_CREATE_USER_ID)
    private String createUserId;
    /**
     * 创建时间
     */
    @TableField(value = FieldConst.COL_CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 最后修改人id
     */
    @TableField(value = FieldConst.COL_LAST_MODIFYER_ID)
    private String lastModifyerId;
    /**
     * 修改时间
     */
    @TableField(value = FieldConst.COL_UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 是否已删除?0:否1:是
     */
    @TableField(value = FieldConst.COL_IS_DELETED)
    private Short isDeleted;
    /**
     * 数据删除时间
     */
    @TableField(value = FieldConst.COL_DELETE_TIME)
    private Date deletedTime;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}