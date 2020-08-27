package com.egg.manager.persistence.pojo.vo.user;

import com.egg.manager.persistence.pojo.vo.MyBaseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJobVo extends MyBaseVo {
    private String fid;
    private String userAccountId;
    private String defineJobId;

    private String remark;
    private Short state;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String lastModifyerId;
    private UserAccountVo createUser;
    private UserAccountVo lastModifyer;


}
