package com.egg.manager.persistence.dto.define;

import com.egg.manager.persistence.dto.MyBaseDto;
import com.egg.manager.persistence.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefineGroupDto extends MyBaseDto {

    private String fid;

    private String name;
    private String pid;
    private Integer isInherit; //是否成员可继承组权限


    private String type;
    private Short state;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;

}
