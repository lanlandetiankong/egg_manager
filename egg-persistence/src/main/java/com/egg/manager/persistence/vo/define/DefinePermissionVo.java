package com.egg.manager.persistence.vo.define;

import com.egg.manager.common.base.enums.base.BaseStateEnum;
import com.egg.manager.common.base.enums.base.SwitchStateEnum;
import com.egg.manager.common.base.enums.permission.DefinePermissionTypeEnum;
import com.egg.manager.persistence.dto.define.DefinePermissionDto;
import com.egg.manager.persistence.entity.define.DefinePermission;
import com.egg.manager.persistence.vo.user.UserAccountVo;
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
public class DefinePermissionVo {
    private String fid ;
    private String name ;
    private String code ;
    private boolean ensure;
    private String ensureStr;
    private Integer type;
    private String typeStr ;

    private String remark ;
    private Integer state ;
    private Date createTime ;
    private Date updateTime ;
    private String createUserId ;
    private String lastModifyerId;
    private UserAccountVo createUser ;
    private UserAccountVo lastModifyer;









    public static DefinePermission transferVoToEntity(DefinePermissionVo definePermissionVo) {
        if(definePermissionVo == null){
            return null ;
        }
        DefinePermission definePermission = new DefinePermission() ;
        definePermission.setFid(definePermissionVo.getFid());
        definePermission.setName(definePermissionVo.getName());
        definePermission.setCode(definePermissionVo.getCode());
        definePermission.setEnsure(definePermissionVo.isEnsure() ? 1 : 0);
        definePermission.setType(definePermissionVo.getType());
        definePermission.setRemark(definePermissionVo.getRemark());
        definePermission.setState(definePermissionVo.getState());
        definePermission.setCreateTime(definePermissionVo.getCreateTime());
        definePermission.setUpdateTime(definePermissionVo.getUpdateTime());
        definePermission.setCreateUserId(definePermissionVo.getCreateUserId());
        definePermission.setLastModifyerId(definePermissionVo.getLastModifyerId());

        return definePermission ;
    }

    public static DefinePermissionVo transferEntityToVo(DefinePermission definePermission) {
        if(definePermission == null){
            return null ;
        }
        DefinePermissionVo definePermissionVo = new DefinePermissionVo() ;
        definePermissionVo.setFid(definePermission.getFid());
        definePermissionVo.setName(definePermission.getName());
        definePermissionVo.setCode(definePermission.getCode());
        definePermissionVo.setEnsure(SwitchStateEnum.Open.getValue().equals(definePermission.getEnsure()));
        definePermissionVo.setEnsureStr(SwitchStateEnum.dealGetNameByVal(definePermission.getEnsure()));
        definePermissionVo.setType(definePermission.getType());
        if(definePermission.getType() != null){
            DefinePermissionTypeEnum typeEnum = DefinePermissionTypeEnum.doGetEnumByValue(definePermission.getType());
            if(typeEnum != null){
                definePermissionVo.setTypeStr(typeEnum.getLabel());
            }   else {
                definePermissionVo.setTypeStr("");
            }
        }
        definePermissionVo.setRemark(definePermission.getRemark());
        definePermissionVo.setState(definePermission.getState());
        definePermissionVo.setCreateTime(definePermission.getCreateTime());
        definePermissionVo.setUpdateTime(definePermission.getUpdateTime());
        definePermissionVo.setCreateUserId(definePermission.getCreateUserId());
        definePermissionVo.setLastModifyerId(definePermission.getLastModifyerId());

        return definePermissionVo ;
    }

    public static DefinePermissionVo transferEntityToVo(DefinePermissionDto definePermissionDto) {
        if(definePermissionDto == null){
            return null ;
        }
        DefinePermissionVo definePermissionVo = new DefinePermissionVo() ;
        definePermissionVo.setFid(definePermissionDto.getFid());
        definePermissionVo.setName(definePermissionDto.getName());
        definePermissionVo.setCode(definePermissionDto.getCode());
        definePermissionVo.setEnsure(SwitchStateEnum.Open.getValue().equals(definePermissionDto.getEnsure()));
        definePermissionVo.setEnsureStr(SwitchStateEnum.dealGetNameByVal(definePermissionDto.getEnsure()));
        definePermissionVo.setType(definePermissionDto.getType());
        if(definePermissionDto.getType() != null){
            DefinePermissionTypeEnum typeEnum = DefinePermissionTypeEnum.doGetEnumByValue(definePermissionDto.getType());
            if(typeEnum != null){
                definePermissionVo.setTypeStr(typeEnum.getLabel());
            }   else {
                definePermissionVo.setTypeStr("");
            }
        }
        definePermissionVo.setRemark(definePermissionDto.getRemark());
        definePermissionVo.setState(definePermissionDto.getState());
        definePermissionVo.setCreateTime(definePermissionDto.getCreateTime());
        definePermissionVo.setUpdateTime(definePermissionDto.getUpdateTime());
        definePermissionVo.setCreateUserId(definePermissionDto.getCreateUserId());
        definePermissionVo.setLastModifyerId(definePermissionDto.getLastModifyerId());
        definePermissionVo.setCreateUser(UserAccountVo.transferEntityToVo(definePermissionDto.getCreateUser()));
        definePermissionVo.setLastModifyer(UserAccountVo.transferEntityToVo(definePermissionDto.getLastModifyer()));
        return definePermissionVo ;
    }

    public static List<DefinePermissionVo> transferEntityToVoList(List<DefinePermission> definePermissions){
        if(definePermissions == null){
            return null ;
        }   else {
            List<DefinePermissionVo> list = new ArrayList<>() ;
            for (DefinePermission definePermission : definePermissions){
                list.add(transferEntityToVo(definePermission));
            }
            return list ;
        }
    }

    public static List<DefinePermissionVo> transferDtoToVoList(List<DefinePermissionDto> definePermissionDtos){
        if(definePermissionDtos == null){
            return null ;
        }   else {
            List<DefinePermissionVo> list = new ArrayList<>() ;
            for (DefinePermissionDto definePermissionDto : definePermissionDtos){
                list.add(transferEntityToVo(definePermissionDto));
            }
            return list ;
        }
    }

}
