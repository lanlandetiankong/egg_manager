package com.egg.manager.persistence.dto.module;

import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineModuleDto{
    private String fid ;

    private String name ;
    private String code ;
    private String icon ;
    private String style ;
    private Integer type;


    private Integer state ;
    private String remark ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;


}
