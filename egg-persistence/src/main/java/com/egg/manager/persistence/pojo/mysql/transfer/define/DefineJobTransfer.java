package com.egg.manager.persistence.pojo.mysql.transfer.define;

import com.egg.manager.persistence.db.mysql.entity.define.DefineJob;
import com.egg.manager.persistence.pojo.mysql.dto.define.DefineJobDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.define.DefineJobMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.define.DefineJobVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("defineJobTransfer")
public class DefineJobTransfer extends MyBaseMysqlTransfer {
    static DefineJobMapstruct defineJobMapstruct = DefineJobMapstruct.INSTANCE ;

    public static DefineJob transferVoToEntity(DefineJobVo vo) {
        if (vo == null) {
            return null;
        }
        DefineJob entity = defineJobMapstruct.transferVoToEntity(vo);
        return entity;
    }

    public static DefineJobVo transferEntityToVo(DefineJob entity) {
        if (entity == null) {
            return null;
        }
        DefineJobVo vo = defineJobMapstruct.transferEntityToVo(entity);
        return vo;
    }

    public static DefineJobVo transferDtoToVo(DefineJobDto dto) {
        if (dto == null) {
            return null;
        }
        DefineJobVo vo = defineJobMapstruct.transferDtoToVo(dto);
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
