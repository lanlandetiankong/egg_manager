package com.egg.manager.persistence.pojo.vo.define;

import com.egg.manager.persistence.pojo.vo.MyBaseVo;
import com.egg.manager.persistence.pojo.vo.user.UserAccountVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineDepartmentVo extends MyBaseVo {
    private String fid;
    private String name;
    private String code;
    private String parentId;
    private DefineDepartmentVo parentDepartment;
    private Integer level;

    private Integer orderNum;
    private String description;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccountVo createUser;
    private UserAccountVo lastModifyer;


}
