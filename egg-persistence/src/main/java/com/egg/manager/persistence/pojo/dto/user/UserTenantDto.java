package com.egg.manager.persistence.pojo.dto.user;

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
public class UserTenantDto extends MyBaseDto {
    private String fid;

    private String userAccountId;
    private String defineTenantId;
    private Integer type;
    private Short isManager;

    private Short state;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;


}
