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
public class DefineJobDto {

    private String fid ;
    private String name ;
    private String description;
    private Integer type;

    private String remark ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;




}
