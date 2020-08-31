package com.egg.manager.persistence.pojo.transfer.mysql.define;

import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineJobDto;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineJobMysqlVo;

import java.util.ArrayList;
import java.util.List;


public class DefineJobTransfer extends MyBaseMysqlTransfer {
    public static DefineJob transferVoToEntity(DefineJobMysqlVo defineJobVo) {
        if (defineJobVo == null) {
            return null;
        }
        DefineJob defineJob = new DefineJob();
        defineJob.setFid(defineJobVo.getFid());
        defineJob.setName(defineJobVo.getName());
        defineJob.setType(defineJobVo.getType());
        defineJob.setDescription(defineJobVo.getDescription());

        defineJob.setRemark(defineJobVo.getRemark());
        defineJob.setState(defineJobVo.getState());
        defineJob.setCreateTime(defineJobVo.getCreateTime());
        defineJob.setUpdateTime(defineJobVo.getUpdateTime());
        defineJob.setCreateUserId(defineJobVo.getCreateUserId());
        defineJob.setLastModifyerId(defineJobVo.getLastModifyerId());
        return defineJob;
    }

    public static DefineJobMysqlVo transferEntityToVo(DefineJob defineJob) {
        if (defineJob == null) {
            return null;
        }
        DefineJobMysqlVo defineJobVo = new DefineJobMysqlVo();
        defineJobVo.setFid(defineJob.getFid());
        defineJobVo.setName(defineJob.getName());
        defineJobVo.setType(defineJob.getType());
        DefineJobTypeEnum defineJobTypeEnum = DefineJobTypeEnum.doGetEnumByValue(defineJob.getType());
        if (defineJobTypeEnum != null) {
            defineJobVo.setTypeStr(defineJobTypeEnum.getLabel());
        }
        defineJobVo.setDescription(defineJob.getDescription());

        defineJobVo.setRemark(defineJob.getRemark());
        defineJobVo.setState(defineJob.getState());
        defineJobVo.setCreateTime(defineJob.getCreateTime());
        defineJobVo.setUpdateTime(defineJob.getUpdateTime());
        defineJobVo.setCreateUserId(defineJob.getCreateUserId());
        defineJobVo.setLastModifyerId(defineJob.getLastModifyerId());
        return defineJobVo;
    }

    public static DefineJobMysqlVo transferDtoToVo(DefineJobDto defineJobDto) {
        if (defineJobDto == null) {
            return null;
        }
        DefineJobMysqlVo defineJobVo = new DefineJobMysqlVo();
        defineJobVo.setFid(defineJobDto.getFid());
        defineJobVo.setName(defineJobDto.getName());
        defineJobVo.setType(defineJobDto.getType());
        DefineJobTypeEnum defineJobTypeEnum = DefineJobTypeEnum.doGetEnumByValue(defineJobDto.getType());
        if (defineJobTypeEnum != null) {
            defineJobVo.setTypeStr(defineJobTypeEnum.getLabel());
        }
        defineJobVo.setDescription(defineJobDto.getDescription());

        defineJobVo.setRemark(defineJobDto.getRemark());
        defineJobVo.setState(defineJobDto.getState());
        defineJobVo.setCreateTime(defineJobDto.getCreateTime());
        defineJobVo.setUpdateTime(defineJobDto.getUpdateTime());
        defineJobVo.setCreateUserId(defineJobDto.getCreateUserId());
        defineJobVo.setLastModifyerId(defineJobDto.getLastModifyerId());
        defineJobVo.setCreateUser(UserAccountTransfer.transferEntityToVo(defineJobDto.getCreateUser()));
        defineJobVo.setLastModifyer(UserAccountTransfer.transferEntityToVo(defineJobDto.getLastModifyer()));
        return defineJobVo;
    }

    public static List<DefineJobMysqlVo> transferEntityToVoList(List<DefineJob> defineJobs) {
        if (defineJobs == null) {
            return null;
        } else {
            List<DefineJobMysqlVo> list = new ArrayList<>();
            for (DefineJob defineJob : defineJobs) {
                list.add(transferEntityToVo(defineJob));
            }
            return list;
        }
    }

    public static List<DefineJobMysqlVo> transferDtoToVoList(List<DefineJobDto> defineJobDtos) {
        if (defineJobDtos == null) {
            return null;
        } else {
            List<DefineJobMysqlVo> list = new ArrayList<>();
            for (DefineJobDto defineJobDto : defineJobDtos) {
                list.add(transferDtoToVo(defineJobDto));
            }
            return list;
        }
    }

}
