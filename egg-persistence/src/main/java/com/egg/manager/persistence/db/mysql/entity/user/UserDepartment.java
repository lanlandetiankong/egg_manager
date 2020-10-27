package com.egg.manager.persistence.db.mysql.entity.user;

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
 * @description: 用户&部门 关联
 * @date 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_user_department")
public class UserDepartment extends Model<UserDepartment> {
    @TableId(value = "fid")
    private String fid;
    /**
     * 用户账号id
     */
    @TableField(value = "user_account_id")
    private String userAccountId;
    /**
     * 部门id
     */
    @TableField(value = "define_department_id")
    private String defineDepartmentId;
    /**
     * 类型
     */
    @TableField("type")
    private Integer type;
    /**
     * 是否部门管理员
     */
    @TableField(value = "is_manager")
    private Short isManager;


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
     * 创建人id
     */
    @TableField(value = "create_user_id")
    private String createUserId;
    /**
     * 最后修改人id
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

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


}
