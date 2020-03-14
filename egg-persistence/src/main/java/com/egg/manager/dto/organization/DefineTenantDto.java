package com.egg.manager.dto.organization;

import com.egg.manager.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineTenantDto {

    private String fid ;

    private String name ;
    private String code ;
    private String dbCode ;

    private Integer state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;

    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;
}
