package com.egg.manager.persistence.vo.organization;

import com.egg.manager.persistence.dto.organization.DefineTenantDto;
import com.egg.manager.persistence.entity.organization.DefineTenant;
import com.egg.manager.persistence.entity.user.UserAccount;
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
public class DefineTenantVo {
    private String fid ;
    private String name ;
    private String code ;
    private String dbCode;
    private String typeStr ;

    private String remark ;
    private Short state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;








}
