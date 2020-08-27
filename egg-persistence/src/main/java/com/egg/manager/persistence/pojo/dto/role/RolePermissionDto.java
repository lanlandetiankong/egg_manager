package com.egg.manager.persistence.pojo.dto.role;

import com.egg.manager.persistence.pojo.dto.MyBaseDto;
import com.egg.manager.persistence.db.mysql.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDto extends MyBaseDto {
    private String fid;
    private String defineRoleId;
    private String definePermissionId;

    private Integer type;

    private Short state;
    private String remark;
    private Date createTime;
    private Date updateTime;

    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;

}
