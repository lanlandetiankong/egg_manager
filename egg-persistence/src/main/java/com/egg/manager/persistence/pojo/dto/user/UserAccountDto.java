package com.egg.manager.persistence.pojo.dto.user;

import com.egg.manager.persistence.pojo.dto.MyBaseDto;
import com.egg.manager.persistence.db.mysql.entity.define.DefineGroup;
import com.egg.manager.persistence.db.mysql.entity.organization.DefineTenant;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto extends MyBaseDto {
    private String fid;

    private String userName;
    private String account;
    private String nickName;
    private String avatarUrl;
    private String password;
    private String phone;
    private String email;
    private Short sex;

    private Integer userType;
    private Integer userTypeNum;
    private String remark;
    private Short state;
    private Short locked;    //是否被锁定
    private Date createTime;
    private Date updateTime;

    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;


    private String belongTenantId;
    private DefineTenant belongTenant;

    private String belongGroupId;
    private DefineGroup belongGroup;

}
