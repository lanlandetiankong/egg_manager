package com.egg.manager.persistence.vo.module;

import com.egg.manager.common.base.enums.module.DefineModuleTypeEnum;
import com.egg.manager.persistence.dto.module.DefineModuleDto;
import com.egg.manager.persistence.entity.module.DefineModule;
import com.egg.manager.persistence.vo.user.UserAccountVo;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
