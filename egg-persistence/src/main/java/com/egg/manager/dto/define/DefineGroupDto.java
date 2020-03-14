package com.egg.manager.dto.define;

import com.egg.manager.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineGroupDto {

    private String fid ;

    private String name ;
    private String pid ;
    private Integer isInherit ; //是否成员可继承组权限


    private String type;
    private Integer state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;

}
