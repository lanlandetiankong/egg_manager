package com.egg.manager.vo.define;

import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
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
    private Integer state ;
    private String remark ;
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
        defineJob.setState(defineJobVo.getState());
        defineJob.setRemark(defineJobVo.getRemark());
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
        defineJobVo.setState(defineJob.getState());
        defineJobVo.setRemark(defineJob.getRemark());
        defineJobVo.setCreateTime(defineJob.getCreateTime());
        defineJobVo.setUpdateTime(defineJob.getUpdateTime());
        defineJobVo.setCreateUserId(defineJob.getCreateUserId());
        defineJobVo.setLastModifyerId(defineJob.getLastModifyerId());
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

}
