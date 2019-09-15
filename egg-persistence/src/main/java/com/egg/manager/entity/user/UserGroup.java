package com.egg.manager.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.egg.manager.entity.define.DefineRole;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("em_user_group")
public class UserGroup extends Model<UserGroup> {
    @TableId
    private String fid ;

    @TableField("define_group_id")
    private String defineGroupId ;
    @TableField("user_account_id")
    private String userAccountId ;

    private String type;
    @Version
    private Integer version ;
    private Integer state ;
    @TableField("create_time")
    private Date createTime ;
    @TableField("update_time")
    private Date updateTime ;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }

}
