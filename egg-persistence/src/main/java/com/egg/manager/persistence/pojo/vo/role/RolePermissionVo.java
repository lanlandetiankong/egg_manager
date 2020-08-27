package com.egg.manager.persistence.pojo.vo.role;

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
public class RolePermissionVo extends MyBaseVo {
    private String fid;
    private String defineRoleId;
    private String definePermissionId;
    private Integer type;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccountVo createUser;
    private UserAccountVo lastModifyer;


}
