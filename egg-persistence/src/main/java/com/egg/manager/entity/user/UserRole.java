package com.egg.manager.entity.user;

import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.entity.BaseEntity;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("user_role")
public class UserRole extends BaseEntity {
    private String userAccountId ;
    private  String userRoleId ;
}
