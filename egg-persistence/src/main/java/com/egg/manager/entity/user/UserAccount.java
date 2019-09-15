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
@TableName("em_user_account")
public class UserAccount extends Model<UserAccount> {
    @TableId
    private String fid ;

    @TableField("user_name")
    private String userName ;
    private String account ;
    @TableField("nick_name")
    private String nickName ;
    @TableField("avatar_url")
    private String avatarUrl ;
    private String phone ;
    private String email ;
    private Integer sex ;
    @TableField("user_type")
    private String userType ;


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
