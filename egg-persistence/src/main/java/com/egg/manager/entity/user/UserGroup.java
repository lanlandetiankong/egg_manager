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
@TableName("user_group")
public class UserGroup extends BaseEntity {
    private String defineGroupId ;
    private String userAccountId ;


}
