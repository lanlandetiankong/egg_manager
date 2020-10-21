package com.egg.manager.persistence.pojo.mysql.transfer.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.imap.user.UserDepartmentMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.BaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserDepartmentVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucj
 * @version V1.0
 * @description:
 * @date 2020/10/20
 */
@Component
@Named("userDepartmentTransfer")
public class UserDepartmentTransfer extends BaseMysqlTransfer {

    static UserDepartmentMapstruct userDepartmentMapstruct = UserDepartmentMapstruct.INSTANCE;

    /**
     * vo转entity
     * @param vo
     * @return
     */
    public static UserDepartment transferVoToEntity(UserDepartmentVo vo) {
        if (vo == null) {
            return null;
        }
        UserDepartment entity = userDepartmentMapstruct.transferVoToEntity(vo);
        return entity;
    }

    /**
     * entity转vo
     * @param entity
     * @return
     */
    public static UserDepartmentVo transferEntityToVo(UserDepartment entity) {
        if (entity == null) {
            return null;
        }
        UserDepartmentVo userTenantVo = userDepartmentMapstruct.transferEntityToVo(entity);
        return userTenantVo;
    }

    /**
     * dto转vo
     * @param dto
     * @return
     */
    public static UserDepartmentVo transferDtoToVo(UserDepartmentDto dto) {
        if (dto == null) {
            return null;
        }
        UserDepartmentVo vo = userDepartmentMapstruct.transferDtoToVo(dto);
        return vo;
    }

    public static List<UserDepartmentVo> transferEntityToVoList(List<UserDepartment> userTenants) {
        if (userTenants == null) {
            return null;
        } else {
            List<UserDepartmentVo> list = new ArrayList<>();
            for (UserDepartment UserDepartment : userTenants) {
                list.add(transferEntityToVo(UserDepartment));
            }
            return list;
        }
    }

    public static List<UserDepartmentVo> transferDtoToVoList(List<UserDepartmentDto> userTenantDtos) {
        if (userTenantDtos == null) {
            return null;
        } else {
            List<UserDepartmentVo> list = new ArrayList<>();
            for (UserDepartmentDto userTenantDto : userTenantDtos) {
                list.add(transferDtoToVo(userTenantDto));
            }
            return list;
        }
    }


}
