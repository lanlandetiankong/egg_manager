package com.egg.manager.persistence.em.define.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description 职务定义
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_define_job")
public class DefineJobEntity extends Model<DefineJobEntity> {

    @TableId(type=IdType.ASSIGN_ID,value = "fid")
    private Long fid;
    /**
     * 名称
     */
    @TableField("name")
    private String name;
    /**
     * 描述
     */
    @TableField("description")
    private String description;
    /**
     * 类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 状态
     */
    @TableField(value = "state", fill = FieldFill.INSERT)
    private Short state;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 创建用户id
     */
    @TableField(value = "create_user_id")
    private Long createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = "last_modifyer_id")
    private Long lastModifyerId;
    /**
     * 版本号
     */
    @Version
    @TableField(value = "version")
    private Integer version;
    /**
     * 是否已经删除，0:否 1:是
     */
    @TableLogic
    @TableField(value = "is_deleted")
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
