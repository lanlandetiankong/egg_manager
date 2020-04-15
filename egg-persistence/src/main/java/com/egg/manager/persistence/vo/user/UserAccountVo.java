package com.egg.manager.persistence.vo.user;

import com.egg.manager.common.base.beans.file.FileResBean;
import com.egg.manager.common.base.enums.user.UserAccountBaseTypeEnum;
import com.egg.manager.persistence.dto.user.UserAccountDto;
import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.vo.organization.DefineTenantVo;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAccountVo {
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
    private String userTypeStr;
    private FileResBean uploadImgBean ;
    private Integer locked ;


    private String remark ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;


    private String belongTenantId ;
    private DefineTenantVo belongTenant ;





}
