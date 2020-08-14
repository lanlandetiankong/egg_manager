package com.egg.manager.persistence.vo.define;


import com.egg.manager.persistence.vo.MyBaseVo;
import com.egg.manager.persistence.vo.user.UserAccountVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineRoleVo extends MyBaseVo {
    private String fid;
    private String name;
    private String code;
    private Integer type;
    private String typeStr;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccountVo createUser;
    private UserAccountVo lastModifyer;


}
