package com.egg.manager.persistence.pojo.transfer.mysql.define;

import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define.DefineJobVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.transfer.mysql.user.UserAccountTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.define.DefineJobVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("DefineJobTransfer")
public class DefineJobTransfer extends MyBaseMysqlTransfer {
    static DefineJobVoMapstruct defineJobVoMapstruct = DefineJobVoMapstruct.INSTANCE ;

    public static DefineJob transferVoToEntity(DefineJobVo vo) {
        if (vo == null) {
            return null;
        }
        DefineJob entity = defineJobVoMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefineJobVo transferEntityToVo(DefineJob entity) {
        if (entity == null) {
            return null;
        }
        DefineJobVo vo = defineJobVoMapstruct.transferEntityToVo(entity);
       //TODO
        DefineJobTypeEnum defineJobTypeEnum = DefineJobTypeEnum.doGetEnumByValue(entity.getType());
        if (defineJobTypeEnum != null) {
            vo.setTypeStr(defineJobTypeEnum.getLabel());
        }
        return vo;
    }

    public static DefineJobVo transferDtoToVo(DefineJobDto dto) {
        if (dto == null) {
            return null;
        }
        DefineJobVo vo = defineJobVoMapstruct.transferDtoToVo(dto);
        //TODO
        DefineJobTypeEnum defineJobTypeEnum = DefineJobTypeEnum.doGetEnumByValue(dto.getType());
        if (defineJobTypeEnum != null) {
            vo.setTypeStr(defineJobTypeEnum.getLabel());
        }
        vo.setCreateUser(UserAccountTransfer.transferEntityToVo(dto.getCreateUser()));
        vo.setLastModifyer(UserAccountTransfer.transferEntityToVo(dto.getLastModifyer()));
        return vo;
    }

    public static List<DefineJobVo> transferEntityToVoList(List<DefineJob> defineJobs) {
        if (defineJobs == null) {
            return null;
        } else {
            List<DefineJobVo> list = new ArrayList<>();
            for (DefineJob defineJob : defineJobs) {
                list.add(transferEntityToVo(defineJob));
            }
            return list;
        }
    }

    public static List<DefineJobVo> transferDtoToVoList(List<DefineJobDto> defineJobDtos) {
        if (defineJobDtos == null) {
            return null;
        } else {
            List<DefineJobVo> list = new ArrayList<>();
            for (DefineJobDto defineJobDto : defineJobDtos) {
                list.add(transferDtoToVo(defineJobDto));
            }
            return list;
        }
    }

}
