package com.egg.manager.persistence.vo.organization;

import com.egg.manager.persistence.entity.user.UserAccount;
import com.egg.manager.persistence.vo.MyBaseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineTenantVo extends MyBaseVo {
    private String fid;
    private String name;
    private String code;
    private String dbCode;
    private String typeStr;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;


}
