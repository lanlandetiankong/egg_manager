package com.egg.manager.persistence.dto.user;

import com.egg.manager.persistence.entity.define.DefineGroup;
import com.egg.manager.persistence.entity.organization.DefineTenant;
import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAccountDto {
    private String fid ;

    private String userName ;
    private String account ;
    private String nickName ;
    private String avatarUrl ;
    private String password ;
    private String phone ;
    private String email ;
    private Integer sex ;

    private Integer userType ;
    private Integer userTypeNum;
    private String remark ;
    private Integer state ;
    private Integer locked ;    //是否被锁定
    private Date createTime ;
    private Date updateTime ;

    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;


    private String belongTenantId ;
    private DefineTenant belongTenant ;

    private String belongGroupId;
    private DefineGroup belongGroup;

}
