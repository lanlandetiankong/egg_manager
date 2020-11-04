package com.egg.manager.persistence.em.user.pojo.transfer;

import com.egg.manager.persistence.em.user.db.mysql.entity.UserRole;
import com.egg.manager.persistence.em.user.pojo.dto.UserRoleDto;
import com.egg.manager.persistence.em.user.pojo.mapstruct.imap.UserRoleMapstruct;
import com.egg.manager.persistence.expand.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.em.user.pojo.vo.UserRoleVo;
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
@Named("userRoleTransfer")
public class UserRoleTransfer extends BaseMysqlTransfer {

    static UserRoleMapstruct userRoleMapstruct = UserRoleMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static UserRole transferVoToEntity(UserRoleVo vo) {
        if (vo == null) {
            return null;
        }
        UserRole entity = userRoleMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static UserRoleVo transferEntityToVo(UserRole entity) {
        if (entity == null) {
            return null;
        }
        UserRoleVo vo = userRoleMapstruct.transferEntityToVo(entity);
        return vo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
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
