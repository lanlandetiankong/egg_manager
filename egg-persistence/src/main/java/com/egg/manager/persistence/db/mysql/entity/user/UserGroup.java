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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("em_user_group")
public class UserGroup extends Model<UserGroup> {
    @TableId
    private String fid;

    @TableField("define_group_id")
    private String defineGroupId;
    @TableField("user_account_id")
    private String userAccountId;
    private String type;

    private String remark;
    private Short state;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField(value = "create_user_id")
    private String createUserId;
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;

    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

}
