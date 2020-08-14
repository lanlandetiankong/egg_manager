package com.egg.manager.persistence.vo.user;

import lombok.*;

import java.util.Date;

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
    private Short isManager;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;






}
