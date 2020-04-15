package com.egg.manager.persistence.vo.user;

import com.egg.manager.persistence.dto.user.UserRoleDto;
import com.egg.manager.persistence.entity.user.UserRole;
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
public class UserRoleVo {
    private String fid;
    private String userAccountId;
    private String defineRoleId;
    private Integer type;

    private String remark;
    private Integer state;
    private Date createTime;
    private Date updateTime;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;



}
