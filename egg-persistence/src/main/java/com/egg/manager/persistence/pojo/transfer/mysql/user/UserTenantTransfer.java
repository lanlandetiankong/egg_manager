package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserTenant;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserTenantDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.user.UserTenantMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserTenantVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("UserTenantTransfer")
public class UserTenantTransfer extends MyBaseMysqlTransfer {

    static UserTenantMapstruct userTenantVoMapstruct = UserTenantMapstruct.INSTANCE ;

    public static UserTenant transferVoToEntity(UserTenantVo vo) {
        if (vo == null) {
            return null;
        }
        UserTenant entity = userTenantVoMapstruct.transferVoToEntity(vo);
        return entity;
    }


    public static UserTenantVo transferEntityToVo(UserTenant entity) {
        if (entity == null) {
            return null;
        }
        UserTenantVo vo = userTenantVoMapstruct.transferEntityToVo(entity);
        return vo;
    }


    public static UserTenantVo transferDtoToVo(UserTenantDto dto) {
        if (dto == null) {
            return null;
        }
        UserTenantVo vo = userTenantVoMapstruct.transferDtoToVo(dto);
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
