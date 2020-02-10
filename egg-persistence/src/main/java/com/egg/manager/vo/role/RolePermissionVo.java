package com.egg.manager.vo.role;

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
    private Integer version ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;
    private String remark;
}
