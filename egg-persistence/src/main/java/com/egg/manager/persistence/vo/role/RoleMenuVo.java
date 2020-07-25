package com.egg.manager.persistence.vo.role;

import com.egg.manager.persistence.dto.role.RoleMenuDto;
import com.egg.manager.persistence.entity.role.RoleMenu;
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
public class RoleMenuVo {
    private String fid;
    private String defineRoleId;
    private String defineMenuId;
    private Integer type;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;







}
