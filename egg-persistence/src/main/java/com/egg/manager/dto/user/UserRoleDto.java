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
public class UserRoleDto {
    private String fid ;

    private String userAccountId ;
    private  String defineRoleId ;
    private Integer type;

    private Integer state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;


}
