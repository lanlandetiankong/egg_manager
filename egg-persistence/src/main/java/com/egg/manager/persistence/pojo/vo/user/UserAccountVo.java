package com.egg.manager.persistence.pojo.vo.user;

import com.egg.manager.common.base.beans.file.FileResBean;
import com.egg.manager.persistence.pojo.vo.MyBaseVo;
import com.egg.manager.persistence.pojo.vo.organization.DefineTenantVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountVo extends MyBaseVo {
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
    private String userTypeStr;
    private FileResBean uploadImgBean;
    private Short locked;


    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccountVo createUser;
    private UserAccountVo lastModifyer;


    private String belongTenantId;
    private DefineTenantVo belongTenant;


}
