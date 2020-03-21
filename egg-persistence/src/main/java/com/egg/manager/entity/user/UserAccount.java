package com.egg.manager.entity.user;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.entity.define.DefineRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    private String password ;
    private String phone ;
    private String email ;
    private Integer sex ;

    @TableField("user_type")
    private Integer userType ;
    @TableField("user_type_num")
    private Integer userTypeNum;

    private String remark ;
    private Integer state ;
    private Integer locked ;    //是否被锁定
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private Date createTime ;
    @TableField(value="update_time",fill = FieldFill.UPDATE)
    private Date updateTime ;
    @TableField(value = "create_user_id")
    private String createUserId ;
    @TableField(value = "last_modifyer_id")
    private String lastModifyerId;


    @Override
    protected Serializable pkVal() {
        return this.fid;
    }


    public static UserAccount dealGetVisitor(){
        return UserAccount.builder().fid("Visitor").userType(UserAccountBaseTypeEnum.Visitor.getValue()).build();
    }
}
