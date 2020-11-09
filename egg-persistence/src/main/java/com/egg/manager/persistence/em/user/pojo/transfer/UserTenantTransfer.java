package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.em.user.db.mysql.entity.UserTenantEntity;
import com.egg.manager.persistence.em.user.pojo.dto.UserTenantDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.UserTenantMapstruct;
import com.egg.manager.persistence.exchange.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.UserTenantVo;
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
@Named("userTenantTransfer")
public class UserTenantTransfer extends BaseMysqlTransfer {

    static UserTenantMapstruct userTenantMapstruct = UserTenantMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static UserTenantEntity transferVoToEntity(UserTenantVo vo) {
        if (vo == null) {
            return null;
        }
        UserTenantEntity entity = userTenantMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static UserTenantVo transferEntityToVo(UserTenantEntity entity) {
        if (entity == null) {
            return null;
        }
        UserTenantVo vo = userTenantMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static UserTenantVo transferDtoToVo(UserTenantDto dto) {
        if (dto == null) {
            return null;
        }
        UserTenantVo vo = userTenantMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<UserTenantVo> transferEntityToVoList(List<UserTenantEntity> userTenantEntities) {
        if (userTenantEntities == null) {
            return null;
        } else {
            List<UserTenantVo> list = new ArrayList<>();
            for (UserTenantEntity userTenantEntity : userTenantEntities) {
                list.add(transferEntityToVo(userTenantEntity));
            }
            return list;
        }
    }

    public static List<UserTenantVo> transferDtoToVoList(List<UserTenantDto> userTenantDtos) {
        if (userTenantDtos == null) {
            return null;
        } else {
            List<UserTenantVo> list = new ArrayList<>();
            for (UserTenantDto userTenantDto : userTenantDtos) {
                list.add(transferDtoToVo(userTenantDto));
            }
            return list;
        }
    }


}
