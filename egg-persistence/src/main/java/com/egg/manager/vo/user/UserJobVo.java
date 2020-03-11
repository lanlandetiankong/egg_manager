package com.egg.manager.vo.user;

import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.entity.user.UserJob;
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
    private Integer state;
    private Date createTime;
    private Date updateTime;

    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;





    public static UserJob transferVoToEntity(UserJobVo userJobVo) {
        if(userJobVo == null){
            return null ;
        }
        UserJob userJob = new UserJob() ;
        userJob.setFid(userJobVo.getFid());
        userJob.setUserAccountId(userJobVo.getUserAccountId());
        userJob.setDefineJobId(userJobVo.getDefineJobId());
        userJob.setState(userJobVo.getState());
        userJob.setCreateTime(userJobVo.getCreateTime());
        userJob.setUpdateTime(userJobVo.getUpdateTime());
        userJob.setCreateUserId(userJobVo.getCreateUserId());
        userJob.setLastModifyerId(userJobVo.getLastModifyerId());
        return userJob ;
    }


    public static UserJobVo transferEntityToVo(UserJob userJob) {
        if(userJob == null){
            return null ;
        }
        UserJobVo userJobVo = new UserJobVo() ;
        userJobVo.setFid(userJob.getFid());
        userJobVo.setUserAccountId(userJob.getUserAccountId());
        userJobVo.setDefineJobId(userJob.getDefineJobId());
        userJobVo.setState(userJob.getState());
        userJobVo.setCreateTime(userJob.getCreateTime());
        userJobVo.setUpdateTime(userJob.getUpdateTime());
        userJobVo.setCreateUserId(userJob.getCreateUserId());
        userJobVo.setLastModifyerId(userJob.getLastModifyerId());
        return userJobVo ;
    }

    public static List<UserJobVo> transferEntityToVoList(List<UserJob> userJobs){
        if(userJobs == null){
            return null ;
        }   else {
            List<UserJobVo> list = new ArrayList<>() ;
            for (UserJob job : userJobs){
                list.add(transferEntityToVo(job));
            }
            return list ;
        }
    }

}
