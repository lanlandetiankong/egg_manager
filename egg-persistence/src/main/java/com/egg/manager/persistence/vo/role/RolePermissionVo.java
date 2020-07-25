package com.egg.manager.persistence.vo.role;

import com.egg.manager.persistence.vo.user.UserAccountVo;
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

    private String remark;
    private Short state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;


}
