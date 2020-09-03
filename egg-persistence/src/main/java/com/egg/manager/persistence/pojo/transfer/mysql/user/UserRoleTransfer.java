package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserRoleDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.vo.user.UserRoleVoMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserRoleVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("UserRoleTransfer")
public class UserRoleTransfer extends MyBaseMysqlTransfer {

    static UserRoleVoMapstruct userRoleVoMapstruct = UserRoleVoMapstruct.INSTANCE ;

    public static UserRole transferVoToEntity(UserRoleVo vo) {
        if (vo == null) {
            return null;
        }
        UserRole entity = userRoleVoMapstruct.transferVoToEntity(vo);
        return entity;
    }


    public static UserRoleVo transferEntityToVo(UserRole entity) {
        if (entity == null) {
            return null;
        }
        UserRoleVo vo = userRoleVoMapstruct.transferEntityToVo(entity);
        return vo;
    }


    public static UserRoleVo transferDtoToVo(UserRoleDto dto) {
        if (dto == null) {
            return null;
        }
        UserRoleVo vo = userRoleVoMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<UserRoleVo> transferEntityToVoList(List<UserRole> userRoles) {
        if (userRoles == null) {
            return null;
        } else {
            List<UserRoleVo> list = new ArrayList<>();
            for (UserRole role : userRoles) {
                list.add(transferEntityToVo(role));
            }
            return list;
        }
    }

    public static List<UserRoleVo> transferDtoToVoList(List<UserRoleDto> userRoleDtos) {
        if (userRoleDtos == null) {
            return null;
        } else {
            List<UserRoleVo> list = new ArrayList<>();
            for (UserRoleDto userRoleDto : userRoleDtos) {
                list.add(transferDtoToVo(userRoleDto));
            }
            return list;
        }
    }


}
