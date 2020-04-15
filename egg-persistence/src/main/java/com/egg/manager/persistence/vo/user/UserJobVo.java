package com.egg.manager.persistence.vo.user;

import com.egg.manager.persistence.dto.user.UserJobDto;
import com.egg.manager.persistence.entity.user.UserJob;
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
public class UserJobVo {
    private String fid;
    private String userAccountId;
    private String defineJobId;

    private String remark;
    private Integer state;
    private Date createTime;
    private Date updateTime;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;




}
