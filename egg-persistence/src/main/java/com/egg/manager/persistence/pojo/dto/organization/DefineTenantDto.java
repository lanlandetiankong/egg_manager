package com.egg.manager.persistence.pojo.dto.organization;

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
public class DefineTenantDto extends MyBaseDto {

    private String fid;

    private String name;
    private String code;
    private String dbCode;

    private Short state;
    private String remark;
    private Date createTime;
    private Date updateTime;

    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;
}
