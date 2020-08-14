package com.egg.manager.persistence.transfer.user;

import com.egg.manager.persistence.dto.user.UserJobDto;
import com.egg.manager.persistence.entity.user.UserJob;
import com.egg.manager.persistence.transfer.MyBaseTransfer;
import com.egg.manager.persistence.vo.user.UserJobVo;

import java.util.ArrayList;
import java.util.List;

public class UserJobTransfer extends MyBaseTransfer {


    public static UserJob transferVoToEntity(UserJobVo userJobVo) {
        if (userJobVo == null) {
            return null;
        }
        UserJob userJob = new UserJob();
        userJob.setFid(userJobVo.getFid());
        userJob.setUserAccountId(userJobVo.getUserAccountId());
        userJob.setDefineJobId(userJobVo.getDefineJobId());
        userJob.setRemark(userJobVo.getRemark());
        userJob.setState(userJobVo.getState());
        userJob.setCreateTime(userJobVo.getCreateTime());
        userJob.setUpdateTime(userJobVo.getUpdateTime());
        userJob.setCreateUserId(userJobVo.getCreateUserId());
        userJob.setLastModifyerId(userJobVo.getLastModifyerId());
        return userJob;
    }


    public static UserJobVo transferEntityToVo(UserJob userJob) {
        if (userJob == null) {
            return null;
        }
        UserJobVo userJobVo = new UserJobVo();
        userJobVo.setFid(userJob.getFid());
        userJobVo.setUserAccountId(userJob.getUserAccountId());
        userJobVo.setDefineJobId(userJob.getDefineJobId());
        userJobVo.setRemark(userJob.getRemark());
        userJobVo.setState(userJob.getState());
        userJobVo.setCreateTime(userJob.getCreateTime());
        userJobVo.setUpdateTime(userJob.getUpdateTime());
        userJobVo.setCreateUserId(userJob.getCreateUserId());
        userJobVo.setLastModifyerId(userJob.getLastModifyerId());
        return userJobVo;
    }


    public static UserJobVo transferDtoToVo(UserJobDto userJobDto) {
        if (userJobDto == null) {
            return null;
        }
        UserJobVo userJobVo = new UserJobVo();
        userJobVo.setFid(userJobDto.getFid());
        userJobVo.setUserAccountId(userJobDto.getUserAccountId());
        userJobVo.setDefineJobId(userJobDto.getDefineJobId());
        userJobVo.setRemark(userJobDto.getRemark());
        userJobVo.setState(userJobDto.getState());
        userJobVo.setCreateTime(userJobDto.getCreateTime());
        userJobVo.setUpdateTime(userJobDto.getUpdateTime());
        userJobVo.setCreateUserId(userJobDto.getCreateUserId());
        userJobVo.setLastModifyerId(userJobDto.getLastModifyerId());
        return userJobVo;
    }

    public static List<UserJobVo> transferEntityToVoList(List<UserJob> userJobs) {
        if (userJobs == null) {
            return null;
        } else {
            List<UserJobVo> list = new ArrayList<>();
            for (UserJob job : userJobs) {
                list.add(transferEntityToVo(job));
            }
            return list;
        }
    }

    public static List<UserJobVo> transferDtoToVoList(List<UserJobDto> userJobDtos) {
        if (userJobDtos == null) {
            return null;
        } else {
            List<UserJobVo> list = new ArrayList<>();
            for (UserJobDto userJobDto : userJobDtos) {
                list.add(transferDtoToVo(userJobDto));
            }
            return list;
        }
    }

}
