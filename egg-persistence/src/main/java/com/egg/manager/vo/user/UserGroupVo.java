package com.egg.manager.vo.user;

import com.egg.manager.entity.user.UserAccount;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Builder
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserGroupVo {
    private String fid ;
    private String defineGroupId ;
    private String userAccountId ;
    private String type;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;

    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;




}
