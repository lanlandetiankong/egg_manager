package com.egg.manager.vo.define;

import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
import com.egg.manager.dto.define.DefineJobDto;
import com.egg.manager.entity.define.DefineJob;
import com.egg.manager.entity.user.UserAccount;
import com.egg.manager.vo.user.UserAccountVo;
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
public class DefineJobVo {
    private String fid ;
    private String name ;
    private Integer type;
    private String typeStr;
    private String description;

    private String remark ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;





    public static DefineJob transferVoToEntity(DefineJobVo defineJobVo) {
        if(defineJobVo == null){
            return null ;
        }
        DefineJob defineJob = new DefineJob() ;
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
        return defineJob ;
    }

    public static DefineJobVo transferEntityToVo(DefineJob defineJob) {
        if(defineJob == null){
            return null ;
        }
        DefineJobVo defineJobVo = new DefineJobVo() ;
        defineJobVo.setFid(defineJob.getFid());
        defineJobVo.setName(defineJob.getName());
        defineJobVo.setType(defineJob.getType());
        DefineJobTypeEnum defineJobTypeEnum = DefineJobTypeEnum.doGetEnumByValue(defineJob.getType());
        if(defineJobTypeEnum != null){
            defineJobVo.setTypeStr(defineJobTypeEnum.getLabel());
        }
        defineJobVo.setDescription(defineJob.getDescription());

        defineJobVo.setRemark(defineJob.getRemark());
        defineJobVo.setState(defineJob.getState());
        defineJobVo.setCreateTime(defineJob.getCreateTime());
        defineJobVo.setUpdateTime(defineJob.getUpdateTime());
        defineJobVo.setCreateUserId(defineJob.getCreateUserId());
        defineJobVo.setLastModifyerId(defineJob.getLastModifyerId());
        return defineJobVo ;
    }

    public static DefineJobVo transferDtoToVo(DefineJobDto defineJobDto) {
        if(defineJobDto == null){
            return null ;
        }
        DefineJobVo defineJobVo = new DefineJobVo() ;
        defineJobVo.setFid(defineJobDto.getFid());
        defineJobVo.setName(defineJobDto.getName());
        defineJobVo.setType(defineJobDto.getType());
        DefineJobTypeEnum defineJobTypeEnum = DefineJobTypeEnum.doGetEnumByValue(defineJobDto.getType());
        if(defineJobTypeEnum != null){
            defineJobVo.setTypeStr(defineJobTypeEnum.getLabel());
        }
        defineJobVo.setDescription(defineJobDto.getDescription());

        defineJobVo.setRemark(defineJobDto.getRemark());
        defineJobVo.setState(defineJobDto.getState());
        defineJobVo.setCreateTime(defineJobDto.getCreateTime());
        defineJobVo.setUpdateTime(defineJobDto.getUpdateTime());
        defineJobVo.setCreateUserId(defineJobDto.getCreateUserId());
        defineJobVo.setLastModifyerId(defineJobDto.getLastModifyerId());
        defineJobVo.setCreateUser(UserAccountVo.transferEntityToVo(defineJobDto.getCreateUser()));
        defineJobVo.setLastModifyer(UserAccountVo.transferEntityToVo(defineJobDto.getLastModifyer()));
        return defineJobVo ;
    }

    public static List<DefineJobVo> transferEntityToVoList(List<DefineJob> defineJobs){
        if(defineJobs == null){
            return null ;
        }   else {
            List<DefineJobVo> list = new ArrayList<>() ;
            for (DefineJob defineJob : defineJobs){
                list.add(transferEntityToVo(defineJob));
            }
            return list ;
        }
    }

    public static List<DefineJobVo> transferDtoToVoList(List<DefineJobDto> defineJobDtos){
        if(defineJobDtos == null){
            return null ;
        }   else {
            List<DefineJobVo> list = new ArrayList<>() ;
            for (DefineJobDto defineJobDto : defineJobDtos){
                list.add(transferDtoToVo(defineJobDto));
            }
            return list ;
        }
    }

}
