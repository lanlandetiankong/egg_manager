package com.egg.manager.persistence.pojo.mysql.transfer.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.pojo.mysql.dto.user.UserDepartmentDto;
import com.egg.manager.persistence.pojo.mysql.mapstruct.user.UserDepartmentMapstruct;
import com.egg.manager.persistence.pojo.mysql.transfer.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.mysql.vo.user.UserDepartmentVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("userDepartmentTransfer")
public class UserDepartmentTransfer extends MyBaseMysqlTransfer {

    static UserDepartmentMapstruct userDepartmentMapstruct = UserDepartmentMapstruct.INSTANCE ;

    public static UserDepartment transferVoToEntity(UserDepartmentVo vo) {
        if (vo == null) {
            return null;
        }
        UserDepartment entity = userDepartmentMapstruct.transferVoToEntity(vo);
        return entity;
    }


    public static UserDepartmentVo transferEntityToVo(UserDepartment entity) {
        if (entity == null) {
            return null;
        }
        UserDepartmentVo userTenantVo = userDepartmentMapstruct.transferEntityToVo(entity);
        return userTenantVo;
    }


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
