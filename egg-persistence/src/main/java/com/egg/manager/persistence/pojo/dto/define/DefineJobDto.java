package com.egg.manager.persistence.pojo.dto.define;

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
public class DefineJobDto extends MyBaseDto {

    private String fid;
    private String name;
    private String description;
    private Integer type;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccount createUser;
    private UserAccount lastModifyer;


}
