package com.egg.manager.dto.user;

import com.egg.manager.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserGroupDto {
    private String fid ;

    private String defineGroupId ;
    private String userAccountId ;

    private String type;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;


}
