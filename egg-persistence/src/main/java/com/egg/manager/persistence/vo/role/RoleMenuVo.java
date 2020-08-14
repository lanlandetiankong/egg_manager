package com.egg.manager.persistence.vo.role;

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
public class RoleMenuVo extends MyBaseVo {
    private String fid;
    private String defineRoleId;
    private String defineMenuId;
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
