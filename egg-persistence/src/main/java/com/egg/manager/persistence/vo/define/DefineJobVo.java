package com.egg.manager.persistence.vo.define;

import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
import com.egg.manager.persistence.dto.define.DefineJobDto;
import com.egg.manager.persistence.entity.define.DefineJob;
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
public class DefineJobVo {
    private String fid ;
    private String name ;
    private Integer type;
    private String typeStr;
    private String description;

    private String remark ;
    private Short state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;






}
