package com.egg.manager.persistence.dto.define;

import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.*;

import java.util.Date;

/**
 * \* note:
 * \* User: zhouchengjie
 * \* Description:
 * \
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DefineDepartmentDto{
    private String fid ;

    private String name ;
    private String code ;
    private String parentId ;
    private DefineDepartmentDto parentDepartment ;
    private Integer level ;
    private Integer orderNum ;
    private String description ;

    private Short state ;
    private String remark;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccount createUser ;
    private UserAccount lastModifyer;

}
