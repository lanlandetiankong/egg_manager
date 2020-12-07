package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.em.user.db.mysql.entity.EmUserDepartmentEntity;
import com.egg.manager.persistence.em.user.pojo.dto.EmUserDepartmentDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.EmUserDepartmentMapstruct;
import com.egg.manager.persistence.em.user.pojo.vo.EmUserDepartmentVo;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
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
@Named("userDepartmentTransfer")
public class EmUserDepartmentTransfer extends BaseMysqlTransfer {

    static EmUserDepartmentMapstruct emUserDepartmentMapstruct = EmUserDepartmentMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static EmUserDepartmentEntity transferVoToEntity(EmUserDepartmentVo vo) {
        if (vo == null) {
            return null;
        }
        EmUserDepartmentEntity entity = emUserDepartmentMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static EmUserDepartmentVo transferEntityToVo(EmUserDepartmentEntity entity) {
        if (entity == null) {
            return null;
        }
        EmUserDepartmentVo userTenantVo = emUserDepartmentMapstruct.transferEntityToVo(entity);
        return userTenantVo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static EmUserDepartmentVo transferDtoToVo(EmUserDepartmentDto dto) {
        if (dto == null) {
            return null;
        }
        EmUserDepartmentVo vo = emUserDepartmentMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<EmUserDepartmentVo> transferEntityToVoList(List<EmUserDepartmentEntity> userTenants) {
        if (userTenants == null) {
            return null;
        } else {
            List<EmUserDepartmentVo> list = new ArrayList<>();
            for (EmUserDepartmentEntity emUserDepartmentEntity : userTenants) {
                list.add(transferEntityToVo(emUserDepartmentEntity));
            }
            return list;
        }
    }

    public static List<EmUserDepartmentVo> transferDtoToVoList(List<EmUserDepartmentDto> userTenantDtos) {
        if (userTenantDtos == null) {
            return null;
        } else {
            List<EmUserDepartmentVo> list = new ArrayList<>();
            for (EmUserDepartmentDto userTenantDto : userTenantDtos) {
                list.add(transferDtoToVo(userTenantDto));
            }
            return list;
        }
    }


}
