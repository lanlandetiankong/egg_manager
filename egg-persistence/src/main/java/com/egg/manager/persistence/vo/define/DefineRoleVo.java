package com.egg.manager.persistence.vo.define;


import com.egg.manager.common.base.enums.role.DefineRoleTypeEnum;
import com.egg.manager.persistence.dto.define.DefineRoleDto;
import com.egg.manager.persistence.entity.define.DefineRole;
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
public class DefineRoleVo {
    private String fid ;
    private String name ;
    private String code ;
    private Integer type;
    private String typeStr ;

    private String remark ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;








}
