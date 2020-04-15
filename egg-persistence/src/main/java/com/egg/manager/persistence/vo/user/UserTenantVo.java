package com.egg.manager.persistence.vo.user;

import com.egg.manager.persistence.dto.user.UserTenantDto;
import com.egg.manager.persistence.entity.user.UserTenant;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTenantVo {
    private String fid;
    private String userAccountId;
    private String defineTenantId;
    private Integer type;
    private Integer isManager;

    private String remark;
    private Integer state;
    private Date createTime;
    private Date updateTime;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;






}
