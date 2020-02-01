package com.egg.manager.vo.user;

import lombok.*;

import java.util.Date;

@Builder
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
    private Integer version ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUser ;
    private String lastModifyer;
}
