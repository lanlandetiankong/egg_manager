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
public class DefineGroupVo {
    private String fid ;
    private String name ;
    private String pid ;
    private Integer isInherit ; //是否成员可继承组权限
    private String type;

    private String remark;
    private Short state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;



}
