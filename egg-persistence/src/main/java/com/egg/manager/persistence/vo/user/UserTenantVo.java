package com.egg.manager.persistence.vo.user;

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
public class UserTenantVo extends MyBaseVo {
    private String fid;
    private String userAccountId;
    private String defineTenantId;
    private Integer type;
    private Short isManager;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccountVo createUser;
    private UserAccountVo lastModifyer;


}
