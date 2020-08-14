package com.egg.manager.persistence.vo.module;

import com.egg.manager.persistence.vo.user.UserAccountVo;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineModuleVo {
    private String fid ;

    private String code ;
    private String name ;
    private String iconVal ;
    private String styleVal ;
    private Integer typeVal;
    private String typeStr;

    private String remark ;
    private Short state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;







}
