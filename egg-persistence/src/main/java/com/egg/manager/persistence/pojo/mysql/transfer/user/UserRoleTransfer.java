package com.egg.manager.persistence.pojo.mysql.transfer.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserRole;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserRoleDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user.UserRoleMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserRoleVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("userRoleTransfer")
public class UserRoleTransfer extends BaseMysqlTransfer {

    static UserRoleMapstruct userRoleMapstruct = UserRoleMapstruct.INSTANCE;

    public static UserRole transferVoToEntity(UserRoleVo vo) {
        if (vo == null) {
            return null;
        }
        UserRole entity = userRoleMapstruct.transferVoToEntity(vo);
        return entity;
    }


    public static UserRoleVo transferEntityToVo(UserRole entity) {
        if (entity == null) {
            return null;
        }
        UserRoleVo vo = userRoleMapstruct.transferEntityToVo(entity);
        return vo;
    }


    public static UserRoleVo transferDtoToVo(UserRoleDto dto) {
        if (dto == null) {
            return null;
        }
        UserRoleVo vo = userRoleMapstruct.transferDtoToVo(dto);
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
