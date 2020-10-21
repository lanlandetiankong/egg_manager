package com.egg.manager.persistence.pojo.mysql.transfer.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user.UserTenantMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserTenantVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @description:
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
    public static UserTenant transferVoToEntity(UserTenantVo vo) {
        if (vo == null) {
            return null;
        }
        UserTenant entity = userTenantMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static UserTenantVo transferEntityToVo(UserTenant entity) {
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

    public static List<UserTenantVo> transferEntityToVoList(List<UserTenant> userTenants) {
        if (userTenants == null) {
            return null;
        } else {
            List<UserTenantVo> list = new ArrayList<>();
            for (UserTenant userTenant : userTenants) {
                list.add(transferEntityToVo(userTenant));
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
