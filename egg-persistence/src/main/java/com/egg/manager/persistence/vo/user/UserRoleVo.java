package com.egg.manager.persistence.vo.user;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRoleVo {
    private String fid;
    private String userAccountId;
    private String defineRoleId;
    private Integer type;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;



}
