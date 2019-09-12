package com.egg.manager.entity.role;

import com.baomidou.mybatisplus.annotations.TableName;
import com.egg.manager.entity.BaseEntity;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("role_permission")
public class RolePermission extends BaseEntity {
    private String defineRoleId;
    private String definePermissionId;

}
