package com.egg.manager.persistence.em.define.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.egg.manager.persistence.commons.base.query.FieldConst;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description 部门定义
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("em_define_department")
public class EmDefineDepartmentEntity extends Model<EmDefineDepartmentEntity> {
    private static final long serialVersionUID = 13269139938733138L;
    @TableId(type = IdType.ASSIGN_ID, value = FieldConst.COL_FID)
    private String fid;
    /**
     * 名称
     */
    @TableField("name")
    private String name;
    /**
     * 编码
     */
    @TableField("code")
    private String code;
    /**
     * 上级id
     */
    @TableField("pid")
    private String pid;
    /**
     * 层级
     */
    @TableField("level")
    private Integer level;
    /**
     * 排序值
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 描述
     */
    @TableField("description")
    private String description;


    /**
     * 备注
     */
    @TableField(FieldConst.COL_REMARK)
    private String remark;
    /**
     * 状态
     */
    @TableField(value = FieldConst.COL_STATE, fill = FieldFill.INSERT)
    private Short state;
    /**
     * 创建时间
     */
    @TableField(value = FieldConst.COL_CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = FieldConst.COL_UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = FieldConst.COL_CREATE_USER_ID)
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = FieldConst.COL_LAST_MODIFYER_ID)
    private String lastModifyerId;
    /**
     * 版本号
     */
    @Version
    @TableField(value = FieldConst.COL_VERSION)
    private Integer version;
    /**
     * 是否已经删除，0:否 1:是
     */
    @TableLogic
    @TableField(value = FieldConst.COL_IS_DELETED)
    private short isDeleted;
    /**
     * 数据删除时间
     */
    @TableField(value = "deleted_time")
    private Date deletedTime;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }
}
