package com.egg.manager.persistence.em.define.pojo.transfer;

import com.egg.manager.persistence.em.define.db.mysql.entity.DefineJob;
import com.egg.manager.persistence.em.define.pojo.dto.DefineJobDto;
import com.egg.manager.persistence.em.define.pojo.mapstruct.imap.DefineJobMapstruct;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.define.pojo.vo.DefineJobVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description
 * @date 2020/10/20
 */
@Component
@Named("defineJobTransfer")
public class DefineJobTransfer extends BaseMysqlTransfer {
    static DefineJobMapstruct defineJobMapstruct = DefineJobMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static DefineJob transferVoToEntity(DefineJobVo vo) {
        if (vo == null) {
            return null;
        }
        DefineJob entity = defineJobMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static DefineJobVo transferEntityToVo(DefineJob entity) {
        if (entity == null) {
            return null;
        }
        DefineJobVo vo = defineJobMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
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
