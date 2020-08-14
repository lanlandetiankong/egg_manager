package com.egg.manager.persistence.dto.define;

import com.egg.manager.persistence.dto.MyBaseDto;
import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineDepartmentDto extends MyBaseDto {
    private String fid;

    private String name;
    private String code;
    private String parentId;
    private DefineDepartmentDto parentDepartment;
    private Integer level;
    private Integer orderNum;
    private String description;

    private Short state;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;

}
