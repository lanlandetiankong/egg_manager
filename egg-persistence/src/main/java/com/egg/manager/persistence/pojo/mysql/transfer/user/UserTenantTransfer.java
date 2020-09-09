package com.egg.manager.persistence.pojo.mysql.transfer.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.user.UserTenantMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserTenantVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("userTenantTransfer")
public class UserTenantTransfer extends MyBaseMysqlTransfer {

    static UserTenantMapstruct userTenantMapstruct = UserTenantMapstruct.INSTANCE ;

    public static UserTenant transferVoToEntity(UserTenantVo vo) {
        if (vo == null) {
            return null;
        }
        UserTenant entity = userTenantMapstruct.transferVoToEntity(vo);
        return entity;
    }


    public static UserTenantVo transferEntityToVo(UserTenant entity) {
        if (entity == null) {
            return null;
        }
        UserTenantVo vo = userTenantMapstruct.transferEntityToVo(entity);
        return vo;
    }


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
