package com.egg.manager.vo.role;

import com.egg.manager.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RolePermissionVo {
    private String fid ;
    private String defineRoleId;
    private String definePermissionId;

    private Integer type;
    private Integer state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;


    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;


}
