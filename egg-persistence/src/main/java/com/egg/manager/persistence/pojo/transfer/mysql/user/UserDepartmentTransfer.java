package com.egg.manager.persistence.pojo.transfer.mysql.user;

import com.egg.manager.persistence.db.mysql.entity.user.UserDepartment;
import com.egg.manager.persistence.pojo.dto.mysql.user.UserDepartmentDto;
import com.egg.manager.persistence.pojo.mapstruct.mysql.user.UserDepartmentMapstruct;
import com.egg.manager.persistence.pojo.transfer.mysql.MyBaseMysqlTransfer;
import com.egg.manager.persistence.pojo.vo.mysql.user.UserDepartmentVo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Named("UserDepartmentTransfer")
public class UserDepartmentTransfer extends MyBaseMysqlTransfer {

    static UserDepartmentMapstruct userDepartmentVoMapstruct = UserDepartmentMapstruct.INSTANCE ;

    public static UserDepartment transferVoToEntity(UserDepartmentVo vo) {
        if (vo == null) {
            return null;
        }
        UserDepartment entity = userDepartmentVoMapstruct.transferVoToEntity(vo);
        return entity;
    }


    public static UserDepartmentVo transferEntityToVo(UserDepartment entity) {
        if (entity == null) {
            return null;
        }
        UserDepartmentVo userTenantVo = userDepartmentVoMapstruct.transferEntityToVo(entity);
        return userTenantVo;
    }


    public static UserDepartmentVo transferDtoToVo(UserDepartmentDto dto) {
        if (dto == null) {
            return null;
        }
        UserDepartmentVo vo = userDepartmentVoMapstruct.transferDtoToVo(dto);
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
