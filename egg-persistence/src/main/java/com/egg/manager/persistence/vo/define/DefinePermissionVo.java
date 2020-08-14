package com.egg.manager.persistence.vo.define;

import com.egg.manager.persistence.vo.user.UserAccountVo;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefinePermissionVo {
    private String fid ;
    private String name ;
    private String code ;
    private boolean ensure;
    private String ensureStr;
    private Integer type;
    private String typeStr ;

    private String remark ;
    private Short state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;











}
