package com.egg.manager.persistence.pojo.transfer.mysql.define;

import com.egg.manager.common.base.enums.define.DefineJobTypeEnum;
import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.pojo.dto.mysql.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.define.DefineJobVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
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
        return vo;
    }

    public static DefineJobVo transferDtoToVo(DefineJobDto dto) {
        if (dto == null) {
            return null;
        }
        DefineJobVo vo = defineJobVoMapstruct.transferDtoToVo(dto);
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


    @Named("handleDefineJobTypeGetGetLabel")
    public String handleDefineJobTypeGetGetLabel(Integer type){
        DefineJobTypeEnum defineJobTypeEnum = DefineJobTypeEnum.doGetEnumByValue(type);
        if (defineJobTypeEnum != null) {
            return defineJobTypeEnum.getLabel();
        }
        return "" ;
    }

}
