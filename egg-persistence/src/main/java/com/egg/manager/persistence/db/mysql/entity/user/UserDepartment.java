package com.egg.manager.persistence.db.mysql.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户&部门 关联-entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_user_department")
public class UserDepartment extends Model<UserDepartment> {
    @TableId
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
    @TableField("state")
    private Short state;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
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


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


}
