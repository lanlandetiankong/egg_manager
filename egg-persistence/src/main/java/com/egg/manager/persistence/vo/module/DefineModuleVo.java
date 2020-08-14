package com.egg.manager.persistence.vo.module;

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
public class DefineModuleVo extends MyBaseVo {
    private String fid;

    private String code;
    private String name;
    private String iconVal;
    private String styleVal;
    private Integer typeVal;
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
