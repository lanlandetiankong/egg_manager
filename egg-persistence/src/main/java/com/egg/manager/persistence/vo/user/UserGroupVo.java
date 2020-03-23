package com.egg.manager.persistence.vo.user;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

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

    private String remark ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;




}
