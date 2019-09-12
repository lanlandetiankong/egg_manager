package com.egg.manager.entity.user;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.entity.BaseEntity;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("user_account")
public class UserAccount extends BaseEntity {
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



}
