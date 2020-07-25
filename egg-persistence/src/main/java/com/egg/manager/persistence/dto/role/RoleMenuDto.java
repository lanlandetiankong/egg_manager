package com.egg.manager.persistence.dto.role;

import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleMenuDto {
    private String fid ;

    private  String defineRoleId ;
    private  String defineMenuId ;
    private Integer type;

    private Short state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;


}
