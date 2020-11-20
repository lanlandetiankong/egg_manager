package com.egg.manager.persistence.em.define.db.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhoucj
 * @description 用户组定义
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("em_define_group")
public class DefineGroupEntity extends Model<DefineGroupEntity> {

    @TableId(type = IdType.ASSIGN_ID, value = "fid")
    private String fid;
    /**
     * 名称
     */
    @TableField("name")
    private String name;
    /**
     * 上级id
     */
    @TableField("pid")
    private String pid;
    /**
     * 是否成员可继承组权限
     */
    @TableField(value = "is_inherit")
    private Integer isInherit;
    /**
     * 类型
     */
    @TableField("type")
    private String type;

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
    private String createUserId;
    /**
     * 最后修改用户id
     */
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;
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
